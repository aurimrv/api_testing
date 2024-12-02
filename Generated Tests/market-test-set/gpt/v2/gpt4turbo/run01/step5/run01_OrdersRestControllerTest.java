
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
    public void testGetOrders_200() {
        String principalName = "ivan.petrov@yandex.ru"; // Registered user
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders")
            .auth().preemptive().basic(principalName, "petrov")
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("$", hasSize(greaterThan(0)))
            .body("id", everyItem(notNullValue()))
            .body("dateCreated", everyItem(matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$")))
            .body("totalCost", everyItem(isA(Double.class)));
    }

    @Test
    public void testGetSingleOrder_200() {
        long orderId = 1; // Assuming order with ID 1 exists
        String principalName = "ivan.petrov@yandex.ru";
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders/{orderId}")
            .pathParam("orderId", orderId)
            .auth().preemptive().basic(principalName, "petrov")
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("id", equalTo((int) orderId))
            .body("userAccount", equalTo(principalName));
    }

    @Test
    public void testGetSingleOrder_404() {
        long orderId = 99999; // Assuming this order ID does not exist
        String principalName = "ivan.petrov@yandex.ru";
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders/{orderId}")
            .pathParam("orderId", orderId)
            .auth().preemptive().basic(principalName, "petrov")
        .when()
            .get()
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrders_401_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders")
        .when()
            .get()
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrders_403_Forbidden() {
        String principalName = "unknown@user.com"; // Unregistered or unauthorized user
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders")
            .auth().preemptive().basic(principalName, "unknownpassword")
        .when()
            .get()
        .then()
            .statusCode(403);
    }
}
