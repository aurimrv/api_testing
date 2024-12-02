
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

public class run01_RestErrorResponseTest {

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
    @Timeout(5)
    public void testValidResponse_for500InternalError_whenFieldError() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(Map.of("email", "invalid_email_format", "name", "Ivan Petrov", "password", "petrov", "phone", "+7 123 456 78 90", "address", "Riesstrasse 18"))
            .when()
            .post("/register")
            .then()
            .statusCode(500);

        String responseDescription = response.extract().path("description");
        assertTrue(responseDescription.contains("Internal Server Error"));
    }

    @Test
    @Timeout(5)
    public void testValidResponse_for404NotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/non_existent_endpoint")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testValidResponse_for400BadRequest_whenMissingRequiredField() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(Map.of("email", "ivan.petrov@yandex.ru", "name", "Ivan Petrov"))  // Missing password
            .when()
            .post("/register")
            .then()
            .statusCode(400);
    }

    @Test
    @Timeout(5)
    public void testValidResponse_for201Created_whenValidUserRegistered() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(Map.of("email", "ivan.petrov@yandex.ru", "name", "Ivan Petrov", "password", "petrov", "phone", "+7 123 456 78 90", "address", "Riesstrasse 18"))
            .when()
            .post("/register")
            .then()
            .statusCode(201);
    }
}
