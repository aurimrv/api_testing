
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetCustomer_authorizedUser_ReturnsOk() {
        String token = obtainValidToken("ivan.petrov@yandex.ru", "petrov");
        given().auth().oauth2(token)
               .when().get(baseUrlOfSut + "/customer")
               .then()
               .statusCode(200)
               .body("email", is("ivan.petrov@yandex.ru"))
               .body("name", is("Ivan Petrov"));
    }

    @Test
    public void testCreateCustomer_existingUser_ReturnsError() {
        given().contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post(baseUrlOfSut + "/register")
               .then()
               .statusCode(500); // Assuming server returns 500 for existing user
    }

    @Test
    public void testCreateCustomer_schemaValidation() {
        given().contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post(baseUrlOfSut + "/register")
               .then()
               .statusCode(201)
               .body(matchesJsonSchemaInClasspath("UserDTORes.json"));
    }

    @Test
    public void testCreateCustomer_invalidInput_ReturnsBadRequest() {
        given().contentType("application/json")
               .body("{\"email\":\"invalid-email\",\"password\":\"123\",\"name\":\"Ivan\",\"phone\":\"123\",\"address\":\"Short\"}")
               .when().post(baseUrlOfSut + "/register")
               .then()
               .statusCode(400);
    }

    private String obtainValidToken(String email, String password) {
        // Implement the method to obtain a valid token based on the email and password provided.
        return "valid-token-for-testing"; // Placeholder for demonstration.
    }
}
