
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
    @Timeout(60)
    public void testGetOrders() {
        // Register user only if it does not exist
        given()
            .contentType("application/json")
            .body("{\"email\":\"unique.ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(anyOf(is(201), is(406)));

        // Authenticate user and get token
        String token = given()
            .contentType("application/json")
            .body("{\"email\":\"unique.ivan.petrov@yandex.ru\",\"password\":\"petrov\"}")
            .post(baseUrlOfSut + "/login")
            .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Get orders for the user
        given()
            .header("Authorization", "Bearer " + token)
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @Timeout(60)
    public void testGetOrder() {
        // Register user only if it does not exist
        given()
            .contentType("application/json")
            .body("{\"email\":\"unique.ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(anyOf(is(201), is(406)));

        // Authenticate user and get token
        String token = given()
            .contentType("application/json")
            .body("{\"email\":\"unique.ivan.petrov@yandex.ru\",\"password\":\"petrov\"}")
            .post(baseUrlOfSut + "/login")
            .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Get order that does not exist
        given()
            .header("Authorization", "Bearer " + token)
            .get(baseUrlOfSut + "/customer/orders/1")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testUnauthorizedAccess() {
        // Attempt to get orders without authentication
        given()
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(401);

        // Attempt to get a specific order without authentication
        given()
            .get(baseUrlOfSut + "/customer/orders/1")
            .then()
            .statusCode(401);
    }
}
