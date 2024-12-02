
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

public class v3_gpt4turbo_run03_CustomerRestControllerTest {

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
    public void testGetCustomerUnauthorized() {
        given().auth().none().when().get(baseUrlOfSut + "/customer").then().statusCode(401);
    }

    @Test
    public void testCreateCustomerExistingEmail() {
        // First create customer
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);
        // Attempt to create the same user again should fail
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov123\", \"name\":\"Ivan Petrovich\", \"phone\":\"+7 123 456 78 99\", \"address\":\"Riesstrasse 19\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(406);
    }

    @Test
    public void testCreateCustomerValidData() {
        given()
            .contentType("application/json")
            .body("{\"email\":\"new.email@yandex.ru\", \"password\":\"newpass\", \"name\":\"New Name\", \"phone\":\"+7 999 888 77 66\", \"address\":\"New Address 123\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("new.email@yandex.ru"))
            .body("name", equalTo("New Name"));
    }

    @Test
    public void testGetCustomerAsAdmin() {
        given().auth().preemptive().basic("admin@market.com", "admin123").when().get(baseUrlOfSut + "/customer").then().statusCode(200);
    }
}
