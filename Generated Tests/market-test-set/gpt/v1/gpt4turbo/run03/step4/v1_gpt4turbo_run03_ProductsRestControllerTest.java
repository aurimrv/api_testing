
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

public class v1_gpt4turbo_run03_ProductsRestControllerTest {

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
        given().baseUri(baseUrlOfSut)
            .when().get("/products")
            .then().statusCode(200)
            .body("size()", greaterThan(0))
            .body("productId", everyItem(notNullValue()))
            .body("name", everyItem(notNullValue()))
            .body("price", everyItem(greaterThan(0.0)))
            .body("volume", everyItem(greaterThanOrEqualTo(1)));
    }

    @Test
    public void testGetProductValidId() {
        long validProductId = 1; // Assuming there is a product with ID 1 in the database for testing
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", validProductId)
            .when().get("/products/{productId}")
            .then().statusCode(200)
            .body("productId", equalTo((int) validProductId))
            .body("name", notNullValue())
            .body("price", greaterThan(0.0))
            .body("volume", greaterThanOrEqualTo(1));
    }

    @Test
    public void testGetProductInvalidId() {
        long invalidProductId = -1; // Non-existent product ID
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", invalidProductId)
            .when().get("/products/{productId}")
            .then().statusCode(404);
    }
}
