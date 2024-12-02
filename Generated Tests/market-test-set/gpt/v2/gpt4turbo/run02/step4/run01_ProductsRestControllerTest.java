
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
    void testGetProductsSuccessful() {
        given().baseUri(baseUrlOfSut)
            .when().get("/products")
            .then().statusCode(200)
            .and().body("size()", is(greaterThan(0)))
            .and().body("productId", everyItem(notNullValue()))
            .and().body("name", everyItem(matchesPattern("^[^#$%^&*()']*$")))
            .and().body("price", everyItem(greaterThan(0.0)))
            .and().body("volume", everyItem(greaterThan(0)));
    }

    @Test
    void testGetProductNotFound() {
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", 999999)
            .when().get("/products/{productId}")
            .then().statusCode(404);
    }

    @Test
    void testGetProductInternalError() {
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", Long.MIN_VALUE)
            .when().get("/products/{productId}")
            .then().statusCode(500);
    }

    @Test
    void testGetProductSuccessful() {
        // Assuming an initial setup where a product with ID 1 exists
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
            .when().get("/products/{productId}")
            .then().statusCode(200)
            .and().body("productId", equalTo(1))
            .and().body("name", matchesPattern("^[^#$%^&*()']*$"))
            .and().body("price", greaterThan(0.0))
            .and().body("volume", greaterThan(0))
            .and().body("_links.self.href", notNullValue());
    }
}
