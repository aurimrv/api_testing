
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
    public void testGetContactsUnauthorized() {
        given()
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetContactsForbidden() {
        given()
            .auth().basic("invalidUser", "invalidPassword")
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(403);
    }

    @Test
    public void testGetContactsNotFound() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        ContactsDTOReq contactsDto = new ContactsDTOReq();
        contactsDto.setAddress("New Address");
        contactsDto.setPhone("+7 123 456 78 90");

        given()
            .body(contactsDto)
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContactsForbidden() {
        ContactsDTOReq contactsDto = new ContactsDTOReq();
        contactsDto.setAddress("New Address");
        contactsDto.setPhone("+7 123 456 78 90");

        given()
            .auth().basic("invalidUser", "invalidPassword")
            .body(contactsDto)
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(403);
    }

    @Test
    public void testUpdateContactsInvalidData() {
        ContactsDTOReq contactsDto = new ContactsDTOReq();
        contactsDto.setAddress("Invalid#Address");
        contactsDto.setPhone("InvalidPhoneNumber");

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .body(contactsDto)
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(400);
    }

    @Test
    public void testUpdateContactsValidData() {
        ContactsDTOReq contactsDto = new ContactsDTOReq();
        contactsDto.setAddress("New Address");
        contactsDto.setPhone("+7 123 456 78 90");

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .body(contactsDto)
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testUpdateContactsInternalServerError() {
        ContactsDTOReq contactsDto = new ContactsDTOReq();
        contactsDto.setAddress("New Address");
        contactsDto.setPhone("+7 123 456 78 90");

        // Simulate a server error
        controller.stopSut();

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .body(contactsDto)
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(500);

        // Restart the SUT
        baseUrlOfSut = controller.startSut();
    }
}
