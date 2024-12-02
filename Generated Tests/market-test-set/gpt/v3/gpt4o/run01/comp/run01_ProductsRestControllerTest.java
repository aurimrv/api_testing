
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import static org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto.*;
import static org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto.*;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_ProductsRestControllerTest {

    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    @BeforeAll
    public static void initClass() {
        controller.setupForGeneratedTest();
        baseUrlOfSut = controller.startSut();
        controller.registerOrExecuteInitSqlCommandsIfNeeded();
        assertNotNull(baseUrlOfSut);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;
        RestAssured.config = RestAssured.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
                .redirect(redirectConfig().followRedirects(false));
    }

    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }

    @BeforeEach
    public void initTest() {
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetProducts() {
        given().
                baseUri(baseUrlOfSut).
        when().
                get("/products").
        then().
                statusCode(200).
                body("$", not(empty()));
    }

    @Test
    public void testGetProductById() {
        // Assuming product with ID 1 exists
        given().
                baseUri(baseUrlOfSut).
        when().
                get("/products/1").
        then().
                statusCode(200).
                body("productId", equalTo(1));
    }

    @Test
    public void testGetProductByInvalidId() {
        given().
                baseUri(baseUrlOfSut).
        when().
                get("/products/9999").
        then().
                statusCode(404);
    }

    @Test
    public void testGetProductByIdInternalError() {
        // Assuming product ID -1 triggers an internal error
        given().
                baseUri(baseUrlOfSut).
        when().
                get("/products/-1").
        then().
                statusCode(500);
    }

    @Test
    public void testGetProductsSchemaValidation() {
        ValidatableResponse response = given().
                baseUri(baseUrlOfSut).
        when().
                get("/products").
        then().
                statusCode(200);
        
        response.assertThat().body("$", not(empty()));
        response.assertThat().body("productId", everyItem(notNullValue()));
        response.assertThat().body("price", everyItem(notNullValue()));
        response.assertThat().body("volume", everyItem(notNullValue()));
        response.assertThat().body("alcohol", everyItem(notNullValue()));
    }

    @Test
    public void testGetProductSchemaValidation() {
        ValidatableResponse response = given().
                baseUri(baseUrlOfSut).
        when().
                get("/products/1").
        then().
                statusCode(200);
        
        response.assertThat().body("productId", notNullValue());
        response.assertThat().body("price", notNullValue());
        response.assertThat().body("volume", notNullValue());
        response.assertThat().body("alcohol", notNullValue());
    }

    @Test
    public void testProductsBusinessRules() {
        // Assuming product with ID 1 exists
        ValidatableResponse response = given().
                baseUri(baseUrlOfSut).
        when().
                get("/products/1").
        then().
                statusCode(200);
        
        response.assertThat().body("available", notNullValue());
        response.assertThat().body("price", greaterThan(0.0));
        response.assertThat().body("volume", greaterThan(0));
        response.assertThat().body("alcohol", greaterThan(0.0));
    }
}
