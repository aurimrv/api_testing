
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

public class v0_gpt4o_run01_CustomerRestControllerTest {

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
        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"new.email@example.com\",\"password\":\"password\",\"name\":\"New User\",\"phone\":\"+1 123 456 7890\",\"address\":\"123 New St\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(201);
        response.body("email", equalTo("new.email@example.com"));
        response.body("name", equalTo("New User"));
        response.body("phone", equalTo("+1 123 456 7890"));
        response.body("address", equalTo("123 New St"));
    }

    @Test
    public void testRegisterCustomerWithExistingEmail() {
        given()
            .contentType("application/json")
            .body("{\"email\":\"existing.email@example.com\",\"password\":\"password\",\"name\":\"Existing User\",\"phone\":\"+1 123 456 7890\",\"address\":\"123 Existing St\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"existing.email@example.com\",\"password\":\"password\",\"name\":\"Existing User\",\"phone\":\"+1 123 456 7890\",\"address\":\"123 Existing St\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(409);
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
        // Register User
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        // Authenticate
        String token = given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\"}")
            .when()
            .post(baseUrlOfSut + "/authenticate")
            .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Get Customer Info
        ValidatableResponse response = given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get(baseUrlOfSut + "/customer")
            .then();

        response.statusCode(200);
        response.body("email", equalTo("ivan.petrov@yandex.ru"));
        response.body("name", equalTo("Ivan Petrov"));
        response.body("phone", equalTo("+7 123 456 78 90"));
        response.body("address", equalTo("Riesstrasse 18"));
    }
}
