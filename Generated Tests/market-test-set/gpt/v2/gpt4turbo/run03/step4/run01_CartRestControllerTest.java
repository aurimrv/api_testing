
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

public class run01_CartRestControllerTest {

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
    public void testGetCart_ErrorScenario_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItem_ErrorScenario_UnknownProduct() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"productId\":9999,\"quantity\":5}")
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    public void testClearCart_SuccessScenario() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(200)
            .body("empty", equalTo(true));
    }

    @Test
    public void testSetDelivery_ErrorScenario_MissingParameter() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(400);
    }

    @Test
    public void testPayByCard_SuccessScenario() {
        String cardJson = "{\"ccNumber\":\"4111111111111111\"}";
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(cardJson)
            .when()
            .post("/customer/cart/pay")
            .then()
            .statusCode(201)
            .body("payed", equalTo(true))
            .body("totalCost", greaterThan(0.0));
    }

    @Test
    public void testPayByCard_ErrorScenario_EmptyCart() {
        String cardJson = "{\"ccNumber\":\"4111111111111111\"}";
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(cardJson)
            .when()
            .post("/customer/cart/pay")
            .then()
            .statusCode(500)
            .body(containsString("EmptyCartException"));
    }

    @Test
    public void testSchemaConformanceForCartDTO() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(200);
        
        JsonPath jsonPathEvaluator = response.extract().jsonPath();
        assertNotNull(jsonPathEvaluator.get("totalItems"));
        assertTrue(jsonPathEvaluator.get("totalItems") instanceof Integer);
        assertNotNull(jsonPathEvaluator.get("productsCost"));
        assertTrue(jsonPathEvaluator.get("productsCost") instanceof Double);
        assertNotNull(jsonPathEvaluator.get("deliveryIncluded"));
        assertTrue(jsonPathEvaluator.get("deliveryIncluded") instanceof Boolean);
    }
}
