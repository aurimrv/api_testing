
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
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
    void testCreateCustomerValid() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        given().contentType("application/json").body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    void testCreateCustomerExistingEmail() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        // Simulate existing user
        given().contentType("application/json").body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(201);

        // Attempt to create same user
        given().contentType("application/json").body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(406)
            .body("message", equalTo("Argument validation error"))
            .body("fieldErrors.field", hasItem("email"))
            .body("fieldErrors.message", hasItem("Account with this email already exists"));
    }

    @Test
    void testGetCustomerUnauthorized() {
        given()
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(401);
    }

    @Test
    void testRegisterCustomerWithInvalidEmail() {
        Map<String, Object> user = Map.of(
            "email", "invalid-email",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        given().contentType("application/json").body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(406)
            .body("message", equalTo("Argument validation error"))
            .body("fieldErrors.field", hasItem("email"))
            .body("fieldErrors.message", hasItem("The value shall be in the format of an email address"));
    }
}
