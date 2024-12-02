
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

public class run01_CartRestControllerTest {

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
    public void testGetCart() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", greaterThanOrEqualTo(0))
            .body("cartItems", notNullValue());
    }

    @Test
    public void testAddItem() {
        JsonPath item = JsonPath.from("{\"productId\":1,\"quantity\":2}");
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(item)
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(200)
            .body("cartItems.size()", greaterThan(0))
            .body("cartItems[0].productId", equalTo(1))
            .body("cartItems[0].quantity", equalTo(2));
    }

    @Test
    public void testClearCart() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", equalTo(0));
    }

    @Test
    public void testSetDelivery() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("included", true)
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testPayByCard() {
        JsonPath card = JsonPath.from("{\"ccNumber\":\"4111111111111111\"}");
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(card)
            .when()
            .post("/customer/cart/pay")
            .then()
            .statusCode(201)
            .body("billNumber", notNullValue())
            .body("_links.self.href", containsString("/orders/"));
    }

    @Test
    public void testInvalidInputCausingError() {
        JsonPath item = JsonPath.from("{\"productId\":999,\"quantity\":-1}");
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(item)
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(anyOf(is(400), is(500))); // Check for either client error (400) or server error (500)
    }
}
