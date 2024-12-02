
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

public class v1_gpt4turbo_run02_RestErrorResponseTest {

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
    public void testGetCustomerNotFound() {
        given().baseUri(baseUrlOfSut)
            .queryParam("name", "NonExistentUser")
        .when()
            .get("/customer")
        .then()
            .statusCode(404)
            .body("message", containsString("User not found"));
    }

    @Test
    public void testRegisterInvalidUser() {
        String requestBody = "{\"email\":\"invalidemail\",\"password\":\"short\",\"name\":\"123\",\"phone\":\"invalid\",\"address\":\"Valid Address\"}";
        
        given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("fieldErrors", hasSize(greaterThan(0)))
            .body("fieldErrors.find { it.path == 'email' }.message", equalTo("Invalid email format"))
            .body("fieldErrors.find { it.path == 'password' }.message", equalTo("Password too short"));
    }

    @Test
    public void testAddItemToCartUnauthorized() {
        String itemJson = "{\"productId\":1,\"quantity\":5}";
        
        given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(itemJson)
            .queryParam("name", "NonLoggedInUser")
        .when()
            .put("/customer/cart")
        .then()
            .statusCode(401)
            .body("message", equalTo("Unauthorized access"));
    }
}
