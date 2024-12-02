
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testAddItem_invalidProductId_expectError() {
        given()
            .contentType("application/json")
            .body("{\"productId\": -1, \"quantity\": 2}")
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(500);
    }

    @Test
    public void testClearCart_emptyCart_expectEmptyCartException() {
        given()
            .delete(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(500)
            .body("message", equalTo("Cart is empty"));
    }

    @Test
    public void testSetDelivery_missingIncludedParam_expectError() {
        given()
            .contentType("application/json")
            .body("{\"included\": null}")
        .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
        .then()
            .statusCode(500);
    }

    @Test
    public void testPayByCard_emptyCart_expectEmptyCartException() {
        given()
            .contentType("application/json")
            .body("{\"ccNumber\": \"1234567812345678\"}")
        .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
        .then()
            .statusCode(500)
            .body("message", equalTo("Cart is empty"));
    }

    // Additional tests for schema validation and business rule enforcement can be added here

}
