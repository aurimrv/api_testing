
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

public class v0_gpt4o_run01_ContactsRestControllerTest {

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
    public void testGetContacts() {
        // Register user
        registerUser();

        // Get contacts
        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", notNullValue())
            .body("phone", notNullValue());

        // Unauthorized case
        given()
            .auth().basic("wrong.user@domain.com", "wrongpassword")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);

        // Forbidden case
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(403);

        // Not found case (assuming a non-existing endpoint for 404)
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/nonexistent")
            .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateContacts() {
        // Register user
        registerUser();

        // Update contacts
        Map<String, Object> contactsDto = Map.of(
            "address", "New Address",
            "phone", "+7 987 654 32 10"
        );

        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 987 654 32 10"));

        // Unauthorized case
        given()
            .auth().basic("wrong.user@domain.com", "wrongpassword")
            .contentType("application/json")
            .body(contactsDto)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);

        // Forbidden case
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(403);

        // Not found case (assuming a non-existing endpoint for 404)
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .put(baseUrlOfSut + "/customer/nonexistent")
            .then()
            .statusCode(404);
    }

    private void registerUser() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        given()
            .contentType("application/json")
            .body(user)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(anyOf(equalTo(201), equalTo(406))); // Allowing both 201 and 406 status codes
    }
}
