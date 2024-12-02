
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
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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

public class v2_gpt4o_run03_OrdersRestControllerTest {

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
    @Timeout(30)
    public void testGetOrders_Success() {
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Timeout(30)
    public void testGetOrder_Success() {
        long orderId = 1; // Assume an order with ID 1 exists for testing
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(200)
            .body("id", equalTo((int)orderId))
            .body("userAccount", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    @Timeout(30)
    public void testGetOrder_NotFound() {
        long orderId = 9999; // Assume this order ID does not exist
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(404);
    }

    @Test
    @Timeout(30)
    public void testGetOrders_Unauthorized() {
        given()
            .auth().preemptive().basic("invalid.user@example.com", "invalidpassword")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(401);
    }

    @Test
    @Timeout(30)
    public void testGetOrder_Unauthorized() {
        long orderId = 1; // Assume an order with ID 1 exists for testing
        given()
            .auth().preemptive().basic("invalid.user@example.com", "invalidpassword")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(401);
    }

    @Test
    @Timeout(30)
    public void testGetOrders_InternalServerError() {
        // Simulate internal server error by causing a failure in the service layer
        // (This part may require specific setup or mocks in the actual service)
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders?causeError=true")
            .then().statusCode(500);
    }

    @Test
    @Timeout(30)
    public void testGetOrder_InternalServerError() {
        long orderId = 1; // Assume an order with ID 1 exists for testing
        // Simulate internal server error by causing a failure in the service layer
        // (This part may require specific setup or mocks in the actual service)
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId + "?causeError=true")
            .then().statusCode(500);
    }

    @Test
    @Timeout(30)
    public void testGetOrders_SchemaValidation() {
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("size()", greaterThanOrEqualTo(0))
            .body("every { it.containsKey('id') }", is(true))
            .body("every { it.containsKey('userAccount') }", is(true));
    }

    @Test
    @Timeout(30)
    public void testGetOrder_SchemaValidation() {
        long orderId = 1; // Assume an order with ID 1 exists for testing
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(200)
            .body("id", equalTo((int)orderId))
            .body("userAccount", equalTo("ivan.petrov@yandex.ru"))
            .body("$", hasKey("_links"))
            .body("$", hasKey("billNumber"))
            .body("$", hasKey("dateCreated"))
            .body("$", hasKey("deliveryCost"))
            .body("$", hasKey("deliveryIncluded"))
            .body("$", hasKey("executed"))
            .body("$", hasKey("payed"))
            .body("$", hasKey("productsCost"))
            .body("$", hasKey("totalCost"));
    }

    @Test
    @Timeout(30)
    public void testGetOrders_Forbidden() {
        given()
            .auth().preemptive().basic("unauthorized.user@example.com", "password")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(403);
    }

    @Test
    @Timeout(30)
    public void testGetOrder_Forbidden() {
        long orderId = 1; // Assume an order with ID 1 exists for testing
        given()
            .auth().preemptive().basic("unauthorized.user@example.com", "password")
            .when().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then().statusCode(403);
    }
}
