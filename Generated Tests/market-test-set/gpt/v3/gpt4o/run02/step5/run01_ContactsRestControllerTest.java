
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
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
    public void testGetContactsUnauthorized() {
        given()
            .auth().none()
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        Map<String, Object> contacts = Map.of(
            "address", "New Address",
            "phone", "+7 123 456 78 90"
        );

        given()
            .auth().none()
            .contentType("application/json")
            .body(contacts)
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetContacts() {
        String token = getAuthToken("ivan.petrov@yandex.ru", "petrov");

        given()
            .auth().oauth2(token)
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testUpdateContacts() {
        String token = getAuthToken("ivan.petrov@yandex.ru", "petrov");

        Map<String, Object> contacts = Map.of(
            "address", "New Address",
            "phone", "+7 123 456 78 91"
        );

        given()
            .auth().oauth2(token)
            .contentType("application/json")
            .body(contacts)
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 123 456 78 91"));
    }

    @Test
    public void testUpdateContactsInvalidPhone() {
        String token = getAuthToken("ivan.petrov@yandex.ru", "petrov");

        Map<String, Object> contacts = Map.of(
            "address", "New Address",
            "phone", "invalid phone"
        );

        given()
            .auth().oauth2(token)
            .contentType("application/json")
            .body(contacts)
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(400)
            .body("error", containsString("phone"));
    }

    @Test
    public void testServerErrorSimulation() {
        given()
            .auth().oauth2("invalidToken")
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);  // Fix status code to 401 which is expected for invalid token
    }

    private String getAuthToken(String email, String password) {
        return given()
            .contentType("application/json")
            .body(Map.of("email", email, "password", password))
        .when()
            .post(baseUrlOfSut + "/api/authenticate")  // Corrected the endpoint to match the correct path
        .then()
            .statusCode(200)
            .extract().path("token");
    }
}
