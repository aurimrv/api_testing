
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
    @Timeout(60)
    public void testGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("alcohol", everyItem(allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(96))));
    }

    @Test
    @Timeout(60)
    public void testGetProduct() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(200)
            .body("productId", equalTo(1))
            .body("alcohol", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(96)));
    }

    @Test
    @Timeout(60)
    public void testGetProductNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 999)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetProductInternalError() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", -1)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(500);
    }

    @Test
    @Timeout(60)
    public void testSchemaValidationOnGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("every { it.containsKey('productId') }", is(true))
            .body("every { it.containsKey('name') }", is(true))
            .body("every { it.containsKey('price') }", is(true));
    }

    @Test
    @Timeout(60)
    public void testBusinessRuleEnforcement() {
        // Assuming business rule: alcohol content must be between 1 and 96
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(200)
            .body("alcohol", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(96)));
    }
}
