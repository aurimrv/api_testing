
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import java.math.BigDecimal;
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

public class v3_gpt4turbo_run01_ProductsRestControllerTest {

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
            .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
            .redirect(redirectConfig().followRedirects(false));
    }

    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }

    @BeforeEach
    public void initTest() {
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    void testGetProducts_validResponse() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].productId", notNullValue())
            .body("[0].name", matchesPattern("^[^#$%^&*()']*$"));
    }

    @Test
    void testGetProduct_validProductId() {
        // Assuming valid productId is 1
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", 1)
        .then()
            .statusCode(200)
            .body("productId", equalTo(1))
            .body("alcohol", both(greaterThanOrEqualTo(new BigDecimal("1.0"))).and(lessThanOrEqualTo(new BigDecimal("96.0"))));
    }

    @Test
    void testGetProduct_invalidProductId() {
        // Assuming invalid productId results in a 404
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", 9999)
        .then()
            .statusCode(404);
    }

    @Test
    void testGetProduct_internalServerError() {
        // Simulating server error by passing an invalid type
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", "invalid")
        .then()
            .statusCode(400);
    }
}
