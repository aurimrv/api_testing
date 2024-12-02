
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
    public void testGetOrdersUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/customer/orders")
        .then()
            .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrdersForbidden() {
        String token = loginUser("invalid_user", "invalid_password");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
        .when()
            .get("/customer/orders")
        .then()
            .statusCode(403);
    }

    @Test
    @Timeout(60)
    public void testGetOrdersSuccess() {
        String token = registerAndLoginUser("ivan.petrov@yandex.ru", "petrov");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
        .when()
            .get("/customer/orders")
        .then()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @Timeout(60)
    public void testGetOrderUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/customer/orders/1")
        .then()
            .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrderForbidden() {
        String token = loginUser("invalid_user", "invalid_password");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
        .when()
            .get("/customer/orders/1")
        .then()
            .statusCode(403);
    }

    @Test
    @Timeout(60)
    public void testGetOrderNotFound() {
        String token = registerAndLoginUser("ivan.petrov@yandex.ru", "petrov");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
        .when()
            .get("/customer/orders/999")
        .then()
            .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetOrderSuccess() {
        String token = registerAndLoginUser("ivan.petrov@yandex.ru", "petrov");

        // Assuming the order with id 1 exists for this user
        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
        .when()
            .get("/customer/orders/1")
        .then()
            .statusCode(200)
            .body("id", is(1));
    }

    private String registerAndLoginUser(String email, String password) {
        // Register the user
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
        .when()
            .post("/register")
        .then()
            .statusCode(201);

        // Login the user
        return loginUser(email, password);
    }

    private String loginUser(String email, String password) {
        // Implement the logic to obtain OAuth2 token after login
        return "fake_oauth2_token"; // Replace with actual token retrieval logic
    }
}
