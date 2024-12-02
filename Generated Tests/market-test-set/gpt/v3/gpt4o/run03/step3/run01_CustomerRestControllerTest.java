
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
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_CustomerRestControllerTest {

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
    public void testRegisterCustomer() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(user.getEmail()))
            .body("name", equalTo(user.getName()))
            .body("phone", equalTo(user.getPhone()))
            .body("address", equalTo(user.getAddress()));
    }

    @Test
    public void testRegisterCustomerWithExistingEmail() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(400)
            .body("message", containsString("Email already in use"));
    }

    @Test
    public void testGetCustomerUnauthorized() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetCustomer() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        String token = given()
            .contentType("application/json")
            .body(Map.of("username", user.getEmail(), "password", user.getPassword()))
        .when()
            .post(baseUrlOfSut + "/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo(user.getEmail()))
            .body("name", equalTo(user.getName()))
            .body("phone", equalTo(user.getPhone()))
            .body("address", equalTo(user.getAddress()));
    }

    @Test
    public void testInternalServerError() {
        given()
            .header("Authorization", "Bearer invalid_token")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(500);
    }
}
