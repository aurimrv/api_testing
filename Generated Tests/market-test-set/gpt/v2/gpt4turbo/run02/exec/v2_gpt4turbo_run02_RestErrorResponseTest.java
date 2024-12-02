
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

public class v2_gpt4turbo_run02_RestErrorResponseTest {

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
    public void testInternalErrorSimulation() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/trigger-error") // Assuming endpoint correctly invokes a 500 error
            .then()
            .statusCode(500);

        String errorDescription = response.extract().body().asString();
        assertTrue(errorDescription.contains("Internal Server Error"));
    }

    @Test
    @Timeout(5)
    public void testSchemaComplianceForContacts() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/contacts?name=Ivan%20Petrov")
            .then()
            .statusCode(200)
            .body("address", matchesPattern("^[^#$%^*()']*$"))
            .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));

        Map<String, Object> contacts = response.extract().body().as(Map.class);
        assertNotNull(contacts.get("address"));
        assertNotNull(contacts.get("phone"));
    }

    @Test
    @Timeout(5)
    public void testBusinessRuleForCartModification() {
        // Simulate adding an item to the cart
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"productId\": 1, \"quantity\": 3}")
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(200)
            .body("cartItems[0].productId", equalTo(1))
            .body("cartItems[0].quantity", equalTo(3));

        // Clear the cart and verify it is empty
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(200)
            .body("empty", equalTo(true));
    }
}
