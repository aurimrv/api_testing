
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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
            .body("size()", greaterThan(0))
            .body("find { it.productId }.productId", notNullValue())
            .body("find { it.alcohol }.alcohol", allOf(greaterThan(1.0), lessThan(96.0)))
            .body("find { it.price }.price", greaterThan(0.0));
    }

    @Test
    public void testGetProduct() {
        // Assuming there's a product with ID 1 in the database for the purpose of this test
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(200)
            .body("productId", equalTo(1))
            .body("_links.self.href", containsString("/products/1"));
    }

    @Test
    public void testGetProductNotFound() {
        // Testing with a non-existing product ID
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 9999)  // Assuming 9999 is an invalid product ID
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testProductSchemaValidation() {
        // Validate schema conformance for the first product returned
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0]", matchesJsonSchemaInClasspath("product-schema.json"));
    }
}
