
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
    public void testGetOrdersUnauthorized() {
        given()
            .auth().none()
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrdersForbidden() {
        given()
            .auth().basic("wrong_user", "wrong_password")
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(403);
    }

    @Test
    public void testGetOrdersSuccess() {
        // Insert a user and an order
        InsertionResultsDto userInsertion = sql().insertInto("USER_ROLE")
            .columns("email", "password", "name", "phone")
            .values("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90")
            .returning("id")
            .execute();

        int userId = userInsertion.getIds().get(0);

        sql().insertInto("CUSTOMER_ORDER")
            .columns("id", "user_id", "totalCost", "productsCost", "deliveryCost", "payed", "executed")
            .values(1, userId, 100.0, 80.0, 20.0, true, false)
            .execute();

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(200)
            .body("$.size()", is(1))
            .body("[0].id", is(1));
    }

    @Test
    public void testGetOrderNotFound() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders/999")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrderSuccess() {
        // Insert a user and an order
        InsertionResultsDto userInsertion = sql().insertInto("USER_ROLE")
            .columns("email", "password", "name", "phone")
            .values("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90")
            .returning("id")
            .execute();

        int userId = userInsertion.getIds().get(0);

        sql().insertInto("CUSTOMER_ORDER")
            .columns("id", "user_id", "totalCost", "productsCost", "deliveryCost", "payed", "executed")
            .values(1, userId, 100.0, 80.0, 20.0, true, false)
            .execute();

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders/1")
        .then()
            .statusCode(200)
            .body("id", is(1))
            .body("totalCost", is(100.0f))
            .body("productsCost", is(80.0f))
            .body("deliveryCost", is(20.0f))
            .body("payed", is(true))
            .body("executed", is(false));
    }
}
