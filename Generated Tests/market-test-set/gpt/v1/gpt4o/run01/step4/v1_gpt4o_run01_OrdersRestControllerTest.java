
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

public class v1_gpt4o_run01_OrdersRestControllerTest {

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

    // Test the endpoint /customer/orders
    @Test
    public void testGetOrders() {
        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/orders")
            .then();

        response.statusCode(200);
        response.body("userAccount", everyItem(equalTo("ivan.petrov@yandex.ru")));
    }

    // Test the endpoint /customer/orders/{orderId}
    @Test
    public void testGetOrder() {
        long orderId = createOrderAndGetId();

        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then();

        response.statusCode(200);
        response.body("id", equalTo(orderId));
    }

    @Test
    public void testGetOrderNotFound() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/orders/99999")
            .then()
            .statusCode(404);
    }

    // Helper method to create an order and get its ID
    private long createOrderAndGetId() {
        // Assuming there's an endpoint to create orders, this is a placeholder
        // to simulate order creation for the sake of testing.
        // Replace this with actual order creation logic if available.
        Map<String, Object> orderData = Map.of("userAccount", "ivan.petrov@yandex.ru");
        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(orderData)
            .when()
            .post(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(201);

        return response.extract().path("id");
    }
}
