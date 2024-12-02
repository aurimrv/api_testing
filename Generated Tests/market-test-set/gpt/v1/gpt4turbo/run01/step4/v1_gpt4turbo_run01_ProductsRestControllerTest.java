
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

public class v1_gpt4turbo_run01_ProductsRestControllerTest {

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
            .body("[0].productId", notNullValue())
            .body("[0].name", matchesPattern("^[^#$%^&*()']*$"))
            .body("[0].price", isA(Double.class))
            .body("[0].volume", isA(Integer.class));
    }

    @Test
    public void testGetProduct() {
        Long productId = 1L; // Assuming a product with ID 1 exists for testing
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(200)
            .body("productId", equalTo(productId.intValue()))
            .body("name", matchesPattern("^[^#$%^&*()']*$"))
            .body("price", isA(Double.class))
            .body("volume", isA(Integer.class));
    }

    @Test
    public void testGetProduct_NotFound() {
        Long productId = 9999L; // Assuming this ID does not exist
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
            .when()
            .get("/products/{productId}")
            .then()
            .statusCode(404);
    }
}
