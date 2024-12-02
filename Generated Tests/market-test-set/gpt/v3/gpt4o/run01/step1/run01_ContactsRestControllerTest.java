
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
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetContactsSuccess() {
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200)
            .body("address", notNullValue())
            .body("phone", notNullValue());

        // Schema validation
        response.body(matchesJsonSchemaInClasspath("ContactsDTORes.json"));
    }

    @Test
    public void testGetContactsUnauthorized() {
        given().when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);
    }

    @Test
    public void testUpdateContactsSuccess() {
        Map<String, Object> contactsDto = Map.of(
            "address", "New Address",
            "phone", "+7 987 654 32 10"
        );

        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 987 654 32 10"));

        // Schema validation
        response.body(matchesJsonSchemaInClasspath("ContactsDTORes.json"));
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        Map<String, Object> contactsDto = Map.of(
            "address", "New Address",
            "phone", "+7 987 654 32 10"
        );

        given().contentType("application/json")
            .body(contactsDto)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);
    }

    @Test
    public void testUpdateContactsInvalidInput() {
        Map<String, Object> contactsDto = Map.of(
            "address", "Invalid Address #$%^",
            "phone", "Invalid Phone"
        );

        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(400);
    }

    @Test
    public void testGetContactsInternalServerError() {
        // Simulate server failure by shutting down the controller
        controller.stopSut();

        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(500);

        // Restart the controller for other tests
        baseUrlOfSut = controller.startSut();
    }

    @Test
    public void testUpdateContactsInternalServerError() {
        // Simulate server failure by shutting down the controller
        controller.stopSut();

        Map<String, Object> contactsDto = Map.of(
            "address", "New Address",
            "phone", "+7 987 654 32 10"
        );

        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(500);

        // Restart the controller for other tests
        baseUrlOfSut = controller.startSut();
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);

        // Schema validation
        response.body(matchesJsonSchemaInClasspath("ContactsDTORes.json"));
    }

    @Test
    public void testUpdateContactsSchemaValidation() {
        Map<String, Object> contactsDto = Map.of(
            "address", "New Address",
            "phone", "+7 987 654 32 10"
        );

        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(contactsDto)
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);

        // Schema validation
        response.body(matchesJsonSchemaInClasspath("ContactsDTORes.json"));
    }
}
