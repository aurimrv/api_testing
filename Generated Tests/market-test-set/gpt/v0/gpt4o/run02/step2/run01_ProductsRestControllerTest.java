
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
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
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
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products")
            .then()
            .statusCode(is(oneOf(200, 401, 403, 404)));

        if (response.extract().statusCode() == 200) {
            response.body("size()", greaterThanOrEqualTo(0));
        }
    }

    @Test
    public void testGetProduct() {
        final long nonExistentProductId = 9999;

        // Create a product for test
        InsertionResultsDto results = insertSampleProduct();
        long productId = results.get(0);

        // Test valid product
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products/" + productId)
            .then()
            .statusCode(is(oneOf(200, 401, 403, 404)));

        if (response.extract().statusCode() == 200) {
            response.body("productId", equalTo((int) productId));
        }

        // Test non-existent product
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products/" + nonExistentProductId)
            .then()
            .statusCode(404);
    }

    private InsertionResultsDto insertSampleProduct() {
        return sql().execute(
            new InsertionDto("PRODUCT")
                .addColumns("ID", "NAME", "DESCRIPTION", "PRICE", "ALCOHOL", "VOLUME", "AGE", "DISTILLERY", "AVAILABLE")
                .addValues(1, "Sample Product", "Description", 100.0, 40.0, 700, 12, "Sample Distillery", true)
        );
    }
}
