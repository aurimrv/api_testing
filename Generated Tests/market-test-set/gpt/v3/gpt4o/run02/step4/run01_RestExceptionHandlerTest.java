
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
import java.util.Arrays;

public class run01_RestExceptionHandlerTest {
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
    @Timeout(60)
    public void testGetCustomerNotFound() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer")
               .then()
               .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetCustomerUnauthorized() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testAddItemToCartWithoutAuth() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"productId\":1, \"quantity\":1}")
               .when().put("/customer/cart")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testClearCartWithoutAuth() {
        given().baseUri(baseUrlOfSut)
               .when().delete("/customer/cart")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testSetDeliveryWithoutAuth() {
        given().baseUri(baseUrlOfSut)
               .queryParam("included", true)
               .when().put("/customer/cart/delivery")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testPayByCardWithoutAuth() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"ccNumber\":\"1234-5678-9876-5432\"}")
               .when().post("/customer/cart/pay")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetContactsNotFound() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/contacts")
               .then()
               .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testUpdateContactsWithInvalidData() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"address\":\"Invalid#Address\", \"phone\":\"invalid-phone\"}")
               .when().put("/customer/contacts")
               .then()
               .statusCode(406);
    }

    @Test
    @Timeout(60)
    public void testGetOrdersWithoutAuth() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/orders")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrderNotFound() {
        given().baseUri(baseUrlOfSut)
               .pathParam("orderId", 9999)
               .when().get("/customer/orders/{orderId}")
               .then()
               .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetProductsUnauthorized() {
        given().baseUri(baseUrlOfSut)
               .when().get("/products")
               .then()
               .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetProductNotFound() {
        given().baseUri(baseUrlOfSut)
               .pathParam("productId", 9999)
               .when().get("/products/{productId}")
               .then()
               .statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testCreateCustomerInvalidData() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"email\":\"invalid-email\", \"password\":\"123\", \"name\":\"Invalid#Name\", \"phone\":\"invalid-phone\", \"address\":\"Invalid#Address\"}")
               .when().post("/register")
               .then()
               .statusCode(406);
    }

    @Test
    @Timeout(60)
    public void testInternalServerError() {
        given().baseUri(baseUrlOfSut)
               .when().get("/trigger-internal-server-error")
               .then()
               .statusCode(500);
    }
}
