
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

public class v1_gpt4turbo_run03_RestErrorResponseTest {

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
    public void testRegisterCustomerBadRequest() {
        String requestBody = "{\n" +
            "  \"email\": \"invalidemail\",\n" +
            "  \"name\": \" \",\n" +
            "  \"password\": \"123\",\n" +
            "  \"phone\": \"invalidphone\",\n" +
            "  \"address\": \"\"\n" +
            "}";

        given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .post("/register")
            .then()
            .statusCode(400)
            .body("message", containsString("Validation failed"))
            .body("fieldErrors", hasSize(greaterThan(0)))
            .body("fieldErrors.path", hasItems("email", "name", "password", "phone"));
    }
    
    @Test
    public void testRegisterCustomerSuccess() {
        String requestBody = "{\n" +
            "  \"email\": \"ivan.petrov@yandex.ru\",\n" +
            "  \"name\": \"Ivan Petrov\",\n" +
            "  \"password\": \"petrov\",\n" +
            "  \"phone\": \"+7 123 456 78 90\",\n" +
            "  \"address\": \"Riesstrasse 18\"\n" +
            "}";

        given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .post("/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }
}
