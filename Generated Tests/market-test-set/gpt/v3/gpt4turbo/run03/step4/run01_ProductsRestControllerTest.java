
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
    public void testGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products")
            .then()
            .statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0))); // Check if the array of products is returned
    }

    @Test
    public void testGetProduct_validId() {
        // Simulate valid product ID scenario
        long productId = 1; // Assuming 1 is a valid product ID
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId)); // Validate the product ID
    }

    @Test
    public void testGetProduct_invalidId() {
        // Simulate invalid product ID scenario
        long productId = -1; // Assuming -1 is an invalid product ID
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(404); // Expect a 404 Not Found response
    }

    @Test
    public void testGetProduct_internalServerError() {
        // Force a server error by providing data that leads to an exception
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", "abc") // Passing a string instead of long, assuming this causes a server error
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(500); // Expect a 500 Internal Server Error response
    }
}
