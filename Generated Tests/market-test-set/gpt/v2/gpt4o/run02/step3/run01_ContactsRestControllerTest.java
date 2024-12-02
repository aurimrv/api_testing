
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
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testUpdateContacts() {
        String updatedContactsJson = "{ \"address\": \"New address 123\", \"phone\": \"+7 987 654 32 10\" }";

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(updatedContactsJson)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200)
            .body("address", equalTo("New address 123"))
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    public void testUpdateContactsInvalidPhone() {
        String invalidContactsJson = "{ \"address\": \"New address 123\", \"phone\": \"invalid-phone\" }";

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(invalidContactsJson)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(400); // Assuming validation will fail and return 400 Bad Request
    }

    @Test
    public void testUpdateContactsInternalServerError() {
        String contactsJson = "{ \"address\": \"New address 123\", \"phone\": \"+7 987 654 32 10\" }";

        // Simulate server error by shutting down the controller
        controller.stopSut();
        
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsJson)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(500);

        // Restart the controller for further tests
        baseUrlOfSut = controller.startSut();
    }

    @Test
    public void testGetContactsUnauthorized() {
        given().when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        String contactsJson = "{ \"address\": \"New address 123\", \"phone\": \"+7 987 654 32 10\" }";

        given().contentType("application/json")
            .body(contactsJson)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);
    }

    @Test
    public void testSchemaValidationOnGetContacts() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/ContactsDTORes.json"));
    }
}
