
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

public class run01_OrdersRestControllerTest {

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
    void testGetOrders() {
        String authHeader = "Bearer validToken";
        given().header("Authorization", authHeader)
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    void testGetOrderById() {
        String authHeader = "Bearer validToken";
        long orderId = 1L; // assuming an order with this ID exists
        given().header("Authorization", authHeader)
            .pathParam("orderId", orderId)
            .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then().statusCode(200)
            .body("id", equalTo((int)orderId));
    }

    @Test
    void testGetOrderByIdNotFound() {
        String authHeader = "Bearer validToken";
        long orderId = 9999L; // assuming no order with this ID exists
        given().header("Authorization", authHeader)
            .pathParam("orderId", orderId)
            .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then().statusCode(404);
    }

    @Test
    void testGetOrdersUnauthorized() {
        given()
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(401);
    }

    @Test
    void testGetOrdersForbidden() {
        String authHeader = "Bearer invalidScopeToken";
        given().header("Authorization", authHeader)
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(403);
    }

    @Test
    void testGetOrdersServerInternalError() {
        // Simulate an internal server error by sending unexpected input
        given().header("Authorization", "Bearer validToken")
            .queryParam("unexpected_param", "unexpected_value")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(500);
    }
}
