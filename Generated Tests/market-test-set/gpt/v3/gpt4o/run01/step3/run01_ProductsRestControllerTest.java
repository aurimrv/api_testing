
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
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
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
    public void testGetAllProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0)))
            .body("productId", everyItem(notNullValue()))
            .body("price", everyItem(notNullValue()))
            .body("volume", everyItem(notNullValue()));
    }

    @Test
    public void testGetProductById() {
        long productId = 1; // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(200)
            .body("productId", equalTo((int)productId));
    }

    @Test
    public void testGetProductByInvalidId() {
        long productId = 999999; // Assuming a product with this ID does not exist
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetProductsWithServerError() {
        // Simulate server error by injecting faulty behavior in the SUT
        controller.getService().simulateError(true);
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(500);
        // Revert the faulty behavior
        controller.getService().simulateError(false);
    }

    @Test
    public void testGetProductByIdWithServerError() {
        long productId = 1; // Assuming a product with ID 1 exists
        // Simulate server error by injecting faulty behavior in the SUT
        controller.getService().simulateError(true);
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(500);
        // Revert the faulty behavior
        controller.getService().simulateError(false);
    }

    @Test
    public void testGetProductsSchemaValidation() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ProductDTOsSchema.json"));
    }

    @Test
    public void testGetProductByIdSchemaValidation() {
        long productId = 1; // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/ProductDTOSchema.json"));
    }

    @Test
    public void testGetProductsBusinessRules() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("price", everyItem(greaterThan(0.0)))
            .body("volume", everyItem(greaterThan(0)));
    }

    @Test
    public void testGetProductByIdBusinessRules() {
        long productId = 1; // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(200)
            .body("price", greaterThan(0.0))
            .body("volume", greaterThan(0));
    }
}
