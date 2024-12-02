
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v2_gpt4turbo_run03_ContactsRestControllerTest {
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
    @Timeout(5)
    public void testGetContacts_ValidResponse() {
        String principalName = "ivan.petrov@yandex.ru";
        ValidatableResponse response = given()
            .auth().preemptive().basic(principalName, "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));

        assertNotNull(response);
    }

    @Test
    @Timeout(5)
    public void testUpdateContacts_ValidUpdate() {
        String principalName = "ivan.petrov@yandex.ru";
        Map<String, Object> requestBody = Map.of(
            "phone", "+7 999 888 77 66",
            "address", "Neue Strasse 5"
        );

        ValidatableResponse response = given()
            .auth().preemptive().basic(principalName, "petrov")
            .contentType("application/json")
            .body(requestBody)
            .when()
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Neue Strasse 5"))
            .body("phone", equalTo("+7 999 888 77 66"));

        assertNotNull(response);
    }

    @Test
    @Timeout(5)
    public void testGetContacts_ErrorScenario() {
        given()
            .auth().preemptive().basic("invalid_user", "wrong_pass")
            .when()
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);  // Unauthorized
    }
}
