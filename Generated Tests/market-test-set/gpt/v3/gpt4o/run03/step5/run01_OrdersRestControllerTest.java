
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
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_OrdersRestControllerTest {

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
    public void testGetOrders() {
        // Test for unauthorized access
        given().auth().none()
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(401);

        // Test for successful retrieval of orders
        String accessToken = getAccessToken("admin", "password");
        given().auth().oauth2(accessToken)
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    public void testGetOrder() {
        // Test for unauthorized access
        given().auth().none()
            .when().get(baseUrlOfSut + "/customer/orders/1")
            .then().statusCode(401);

        // Test for order not found
        String accessToken = getAccessToken("admin", "password");
        given().auth().oauth2(accessToken)
            .when().get(baseUrlOfSut + "/customer/orders/999")
            .then().statusCode(404);

        // Test for successful retrieval of an order
        given().auth().oauth2(accessToken)
            .when().get(baseUrlOfSut + "/customer/orders/1")
            .then().statusCode(200)
            .body("id", equalTo(1));
    }

    @Test
    public void testInternalServerError() {
        // Simulate server failure for 5xx status code
        String accessToken = getAccessToken("admin", "password");
        given().auth().oauth2(accessToken)
            .when().get(baseUrlOfSut + "/customer/orders/error")
            .then().statusCode(500);
    }

    private String getAccessToken(String username, String password) {
        return given()
            .contentType("application/json")
            .body(Map.of("username", username, "password", password))
            .when().post(baseUrlOfSut + "/api/authenticate")
            .then().statusCode(200)
            .extract().path("id_token");
    }
}
