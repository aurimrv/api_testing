
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
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
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
    public void testGetCustomerUnauthorized() {
        given()
            .auth().none()
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetCustomerForbidden() {
        given()
            .auth().basic("user", "wrongpassword")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(403);
    }

    @Test
    public void testCreateCustomerSuccess() {
        String email = "ivan.petrov@yandex.ru";
        String payload = "{ \"email\": \"" + email + "\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }";

        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(email))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testCreateCustomerDuplicateEmail() {
        String email = "ivan.petrov@yandex.ru";
        String payload = "{ \"email\": \"" + email + "\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }";

        // Create the first customer
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Try to create a customer with the same email again
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(409); // Assuming 409 Conflict for duplicate email
    }

    @Test
    public void testCreateCustomerInvalidPayload() {
        String payload = "{ \"email\": \"invalidemail\", \"password\": \"123\", \"name\": \"\", \"phone\": \"invalidphone\", \"address\": \"\" }";

        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(400);
    }

    @Test
    public void testCreateCustomerServerError() {
        String payload = "{ \"email\": \"ivan.petrov@yandex.ru\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }";

        // Simulate server error by temporarily disabling the user service
        controller.executeSql("INSERT INTO SERVICE_CONTROL (service, status) VALUES ('userAccountService', 'DOWN')");

        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(500);

        // Restart the user service for further tests
        controller.executeSql("UPDATE SERVICE_CONTROL SET status = 'UP' WHERE service = 'userAccountService'");
    }

    @Test
    public void testGetCustomerNotFound() {
        given()
            .auth().basic("nonexistent@example.com", "password")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetCustomerSuccess() {
        String email = "ivan.petrov@yandex.ru";
        String payload = "{ \"email\": \"" + email + "\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }";

        // Create the customer
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Authenticate and get customer
        given()
            .auth().basic(email, "petrov")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo(email))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }
}
