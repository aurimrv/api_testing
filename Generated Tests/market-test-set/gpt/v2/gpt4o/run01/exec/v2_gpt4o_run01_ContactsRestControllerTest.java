
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

public class v2_gpt4o_run01_ContactsRestControllerTest {
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
        ValidatableResponse response = given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
        
        // Schema validation
        response.assertThat()
            .body("$", hasKey("address"))
            .body("$", hasKey("phone"))
            .body("address", matchesPattern("^[^#$%^*()']*$"))
            .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));
    }

    @Test
    public void testUpdateContacts() {
        Map<String, Object> newContacts = Map.of(
            "address", "New Address 123",
            "phone", "+7 987 654 32 10"
        );
        
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(newContacts)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("New Address 123"))
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    public void testUpdateContactsInvalidPhone() {
        Map<String, Object> invalidContacts = Map.of(
            "address", "New Address 123",
            "phone", "invalid-phone-number"
        );

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(invalidContacts)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(400);
    }

    @Test
    public void testInternalServerError() {
        // Simulate internal server error by invalid input
        Map<String, Object> invalidContacts = Map.of(
            "address", "",
            "phone", ""
        );

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(invalidContacts)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(500);
    }
}
