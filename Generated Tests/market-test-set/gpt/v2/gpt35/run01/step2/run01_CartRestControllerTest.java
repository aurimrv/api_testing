
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
    public void testGetCart_WithValidUser_ShouldReturnCart() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testAddItem_WithValidInput_ShouldReturnUpdatedCart() {
        CartItemDTO item = new CartItemDTO(1L, 2);
        given()
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testClearCart_WithValidUser_ShouldReturnClearedCart() {
        given()
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testSetDelivery_WithValidInput_ShouldReturnUpdatedCart() {
        given()
            .param("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testPayByCard_WithValidInput_ShouldReturnCreatedOrder() {
        CreditCardDTO card = new CreditCardDTO("1234 5678 9012 3456");
        given()
            .body(card)
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testGetCart_WithInvalidUser_ShouldReturnUnauthorized() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
    
    // Add more tests to cover edge cases, error scenarios, schema validation, and business rule enforcement

}
