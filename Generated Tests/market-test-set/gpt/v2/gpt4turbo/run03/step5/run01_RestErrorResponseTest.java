
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    public void testInvalidInputTriggering500Error() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/invalidEndpoint")
            .then()
            .statusCode(404)
            .body("error", is("Not Found"));
    }

    @Test
    public void testValidResponseSchemaCustomer() {
        String encodedName = URLEncoder.encode("Ivan Petrov", StandardCharsets.UTF_8.toString());
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", encodedName)
            .when()
            .get("/customer")
            .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));
    }

    @Test
    public void testUpdateContactsFollowsBusinessRules() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"address\":\"Updated Riesstrasse 18\", \"phone\":\"+7 123 456 78 90\"}")
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(401)
            .body("message", equalTo("Acesso negado"))
            .body("description", equalTo("uri=/customer/contacts"));
    }
}
