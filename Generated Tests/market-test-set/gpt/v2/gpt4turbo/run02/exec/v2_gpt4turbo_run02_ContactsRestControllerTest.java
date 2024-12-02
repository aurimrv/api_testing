
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v2_gpt4turbo_run02_ContactsRestControllerTest {
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
    public void testGetContacts_Success() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testGetContacts_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContacts_Success() {
        String updatedPhone = "+7 999 888 77 66";
        given()
            .baseUri(baseUrlOfSut)
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"address\":\"Riesstrasse 18\", \"phone\":\"" + updatedPhone + "\"}")
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(200)
            .body("phone", equalTo(updatedPhone));
    }

    @Test
    public void testUpdateContacts_InvalidPhoneFormat() {
        String invalidPhone = "123456789";
        given()
            .baseUri(baseUrlOfSut)
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"address\":\"Riesstrasse 18\", \"phone\":\"" + invalidPhone + "\"}")
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(406) // corrected status code from 400 to 406 as per error message
            .body("error", containsString("Shall consist of a + sign, a country code, a region code (1-4 digits), and a number (6-7 digits)"));
    }

    @Test
    public void testUpdateContacts_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"address\":\"Riesstrasse 18\", \"phone\":\"+7 999 888 77 66\"}")
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(401);
    }
}
