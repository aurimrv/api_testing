
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
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expectationHandler;
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
    @Timeout(60)
    public void testCreateCustomer() {
        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(201);
        response.body("email", equalTo("ivan.petrov@yandex.ru"));
        response.body("name", equalTo("Ivan Petrov"));
        response.body("phone", equalTo("+7 123 456 78 90"));
        response.body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(60)
    public void testCreateCustomerEmailExists() {
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(409); // Assuming 409 Conflict for email already exists
    }

    @Test
    @Timeout(60)
    public void testGetCustomer() {
        // Register a new user
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);

        // Get the customer details
        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then();

        response.statusCode(200);
        response.body("email", equalTo("ivan.petrov@yandex.ru"));
        response.body("name", equalTo("Ivan Petrov"));
        response.body("phone", equalTo("+7 123 456 78 90"));
        response.body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(60)
    public void testGetCustomerUnauthorized() {
        ValidatableResponse response = given()
            .when()
            .get(baseUrlOfSut + "/customer")
            .then();

        response.statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testInternalServerError() {
        // Simulate server error by sending invalid data that causes server to crash or fail
        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"invalid-email-format\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(500);
    }

    @Test
    @Timeout(60)
    public void testSchemaValidation() {
        ValidatableResponse response = given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post(baseUrlOfSut + "/register")
            .then();

        response.statusCode(201);
        response.body(matchesJsonSchemaInClasspath("schemas/UserDTORes.json"));
    }
}
