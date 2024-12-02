
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
    @Timeout(5)
    public void testCreateCustomerWithExistingEmail() {
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().contentType("application/json").body(userJson).post(baseUrlOfSut + "/register").then().statusCode(201);
        // Attempt to create the same user again
        given().contentType("application/json").body(userJson).post(baseUrlOfSut + "/register").then().statusCode(403);
    }

    @Test
    @Timeout(5)
    public void testGetCustomerUnauthorized() {
        given().get(baseUrlOfSut + "/customer").then().statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testCreateCustomerSuccess() {
        String userJson = "{\"email\":\"new.user@yandex.ru\",\"password\":\"newuser\",\"name\":\"New User\",\"phone\":\"+7 987 654 32 10\",\"address\":\"New Address\"}";
        given().contentType("application/json").body(userJson)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("new.user@yandex.ru"))
            .body("name", equalTo("New User"))
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    @Timeout(5)
    public void testRegisterCustomerInvalidData() {
        String userJson = "{\"email\":\"invalid-email\",\"password\":\"123\",\"name\":\"\",\"phone\":\"invalid-phone\",\"address\":\"\"}";
        given().contentType("application/json").body(userJson)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(400);
    }

    @Test
    @Timeout(5)
    public void testGetCustomerSuccessful() {
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().contentType("application/json").body(userJson).post(baseUrlOfSut + "/register").then().statusCode(201);
        given().auth().basic("ivan.petrov@yandex.ru", "petrov").get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }
}
