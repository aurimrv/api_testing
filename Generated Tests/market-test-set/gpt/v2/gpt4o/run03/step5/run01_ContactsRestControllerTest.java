
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class run01_ContactsRestControllerTest {

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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetContacts() {
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));

        validateResponseSchema(response, "schemas/ContactsDTORes.json");
    }

    @Test
    public void testUpdateContacts() {
        String updatedContactsJson = "{\"address\":\"New Address\", \"phone\":\"+7 123 456 78 91\"}";

        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(updatedContactsJson)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 123 456 78 91"));

        validateResponseSchema(response, "schemas/ContactsDTORes.json");
    }

    @Test
    public void testUpdateContactsInvalidPhone() {
        String invalidPhoneJson = "{\"address\":\"New Address\", \"phone\":\"invalid_phone\"}";

        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(invalidPhoneJson)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(400);
    }

    @Test
    public void testGetContactsUnauthorized() {
        given()
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContactsServerError() {
        String validContactsJson = "{\"address\":\"Riesstrasse 18\", \"phone\":\"+7 123 456 78 90\"}";

        // Simulating server failure by stopping the SUT
        controller.stopSut();

        try {
            given()
                .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
                .contentType("application/json")
                .body(validContactsJson)
                .put(baseUrlOfSut + "/customer/contacts")
                .then()
                .statusCode(500);
        } catch (Exception e) {
            // Expected exception due to server being down
        } finally {
            // Restore server
            baseUrlOfSut = controller.startSut();
        }
    }

    private void validateResponseSchema(ValidatableResponse response, String schemaPath) {
        response.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}
