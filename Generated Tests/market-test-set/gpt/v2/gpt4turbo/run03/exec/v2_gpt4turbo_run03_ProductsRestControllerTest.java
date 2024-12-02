
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

public class v2_gpt4turbo_run03_ProductsRestControllerTest {

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
        given().when().get(baseUrlOfSut + "/products")
            .then().statusCode(200)
            .body("size()", greaterThan(0))
            .body("productId", everyItem(notNullValue()))
            .body("name", everyItem(notNullValue()))
            .body("price", everyItem(greaterThanOrEqualTo(0.0)));
    }

    @Test
    public void testGetProductWithValidId() {
        long validProductId = 1; // Assuming product with ID 1 exists
        given().pathParam("productId", validProductId)
            .when().get(baseUrlOfSut + "/products/{productId}")
            .then().statusCode(200)
            .body("productId", equalTo((int) validProductId))
            .body("name", notNullValue())
            .body("price", greaterThanOrEqualTo(0.0));
    }

    @Test
    public void testGetProductWithInvalidId() {
        long invalidProductId = 999999; // Assuming this ID does not exist
        given().pathParam("productId", invalidProductId)
            .when().get(baseUrlOfSut + "/products/{productId}")
            .then().statusCode(404);
    }

    @Test
    public void testInternalServerError() {
        // Simulating a server error by giving an impossible scenario or by mocking
        given().when().get(baseUrlOfSut + "/products/causeError")
            .then().statusCode(500);
    }
}
