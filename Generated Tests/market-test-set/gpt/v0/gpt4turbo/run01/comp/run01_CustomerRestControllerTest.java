
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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    void testGetCustomerAuthorized_userRole() {
        String existingUserEmail = "ivan.petrov@yandex.ru";
        given().auth().basic(existingUserEmail, "petrov")
               .when().get(baseUrlOfSut + "/customer")
               .then().statusCode(200)
               .body("email", equalTo(existingUserEmail));
    }

    @Test
    void testGetCustomerUnauthorized() {
        given()
               .when().get(baseUrlOfSut + "/customer")
               .then().statusCode(401);
    }

    @Test
    void testCreateCustomerAlreadyExists() {
        Map<String, Object> newUser = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );
        given().contentType("application/json")
               .body(newUser)
               .when().post(baseUrlOfSut + "/register")
               .then().statusCode(403);
    }

    @Test
    void testCreateCustomerSuccess() {
        Map<String, Object> newUser = Map.of(
            "email", "new.user@yandex.ru",
            "password", "newUserPass",
            "name", "New User",
            "phone", "+7 987 654 32 10",
            "address", "New Address"
        );
        given().contentType("application/json")
               .body(newUser)
               .when().post(baseUrlOfSut + "/register")
               .then().statusCode(201)
               .body("email", equalTo("new.user@yandex.ru"));
    }
}
