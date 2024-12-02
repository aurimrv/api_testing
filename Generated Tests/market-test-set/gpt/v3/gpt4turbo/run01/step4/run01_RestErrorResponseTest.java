
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testInternalServerError() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when()
            .get("/nonexistentEndpoint") // assuming this endpoint doesn't exist and will trigger a 500 error
            .then()
            .assertThat()
            .statusCode(is(500))
            .body("message", containsString("Internal Server Error"));

        assertNotNull(response);
    }

    @Test
    public void testAddFieldError() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"path\": \"name\", \"message\": \"Invalid name\"}")
            .when()
            .post("/addInvalidFieldError") // assuming this endpoint processes the field error addition
            .then()
            .assertThat()
            .statusCode(is(200))
            .body("fieldErrors", hasSize(1))
            .body("fieldErrors[0].path", equalTo("name"))
            .body("fieldErrors[0].message", equalTo("Invalid name"));

        assertNotNull(response);
    }

    @Test
    public void testValidResponse() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when()
            .get("/customer/cart")
            .then()
            .assertThat()
            .statusCode(is(200))
            .body("totalItems", greaterThanOrEqualTo(0))
            .body("totalCost", isA(Double.class))
            .body("deliveryIncluded", isA(Boolean.class));

        assertNotNull(response);
    }
}
