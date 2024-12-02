
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

public class v1_gpt4o_run03_OrdersRestControllerTest {

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
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testGetOrderById() {
        long orderId = 1; // Ensure that order with ID 1 exists in the setup
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders/" + orderId)
        .then()
            .statusCode(200)
            .body("id", equalTo(orderId));
    }

    @Test
    public void testGetOrderById_NotFound() {
        long invalidOrderId = 9999; // Ensure this order ID does not exist in the setup
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders/" + invalidOrderId)
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrders_Unauthorized() {
        given()
            .auth().basic("invalid.user@example.com", "wrongpassword")
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrderById_Unauthorized() {
        long orderId = 1; // Ensure that order with ID 1 exists in the setup
        given()
            .auth().basic("invalid.user@example.com", "wrongpassword")
        .when()
            .get(baseUrlOfSut + "/customer/orders/" + orderId)
        .then()
            .statusCode(401);
    }
}
