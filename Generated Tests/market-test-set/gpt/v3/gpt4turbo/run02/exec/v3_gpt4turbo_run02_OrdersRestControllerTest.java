
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

public class v3_gpt4turbo_run02_OrdersRestControllerTest {

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
    public void testGetOrders() {
        String principalName = "ivan.petrov@yandex.ru";

        given().auth().preemptive().basic(principalName, "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    public void testGetOrder() {
        long orderId = 1;
        String principalName = "ivan.petrov@yandex.ru";

        given().auth().preemptive().basic(principalName, "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(200)
            .body("id", equalTo((int) orderId));
    }

    @Test
    public void testGetOrderNotFound() {
        long orderId = 99999;
        String principalName = "ivan.petrov@yandex.ru";

        given().auth().preemptive().basic(principalName, "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(404);
    }

    @Test
    public void testGetOrderUnauthorized() {
        long orderId = 1;

        given()
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(401);
    }

    @Test
    public void testServerErrorSimulation() {
        // Simulate a condition that would lead to a server error (e.g., by mangling internal state or inputs)
        // Making an unsupported request to simulate server error
        given()
            .when().delete(baseUrlOfSut + "/customer/orders")
            .then().statusCode(anyOf(is(500), is(502), is(503), is(504)));
    }
}
