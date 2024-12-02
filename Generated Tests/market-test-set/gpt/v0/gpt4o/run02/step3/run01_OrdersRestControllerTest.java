
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
    public void testGetOrders() {
        // Register a user
        String user = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given()
            .contentType("application/json")
            .body(user)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        // Authenticate and get token
        String token = given()
            .contentType("application/x-www-form-urlencoded")
            .formParam("username", "ivan.petrov@yandex.ru")
            .formParam("password", "petrov")
            .post(baseUrlOfSut + "/login")
            .then()
            .statusCode(200)
            .extract().path("token");

        // Get orders for the registered user
        given()
            .header("Authorization", "Bearer " + token)
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(200)
            .body("size()", is(0));  // Assuming no orders initially

        // Unauthorized access
        given()
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrder() {
        // Register a user
        String user = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given()
            .contentType("application/json")
            .body(user)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        // Authenticate and get token
        String token = given()
            .contentType("application/x-www-form-urlencoded")
            .formParam("username", "ivan.petrov@yandex.ru")
            .formParam("password", "petrov")
            .post(baseUrlOfSut + "/login")
            .then()
            .statusCode(200)
            .extract().path("token");

        // Insert an order for the user in the database (assuming order ID is 1)
        sql(List.of(
            new InsertionDto()
                .setTableName("CUSTOMER_ORDER")
                .set("id", 1)
                .set("user_account", "ivan.petrov@yandex.ru")
                .set("products_cost", 100.0)
                .set("total_cost", 110.0)
                .set("delivery_cost", 10)
                .set("delivery_included", true)
                .set("executed", false)
                .set("payed", false)
        ));

        // Get the order
        given()
            .header("Authorization", "Bearer " + token)
            .get(baseUrlOfSut + "/customer/orders/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("productsCost", equalTo(100.0F))
            .body("totalCost", equalTo(110.0F))
            .body("deliveryCost", equalTo(10))
            .body("deliveryIncluded", equalTo(true))
            .body("executed", equalTo(false))
            .body("payed", equalTo(false));

        // Unauthorized access
        given()
            .get(baseUrlOfSut + "/customer/orders/1")
            .then()
            .statusCode(401);

        // Order not found
        given()
            .header("Authorization", "Bearer " + token)
            .get(baseUrlOfSut + "/customer/orders/999")
            .then()
            .statusCode(404);
    }
}
