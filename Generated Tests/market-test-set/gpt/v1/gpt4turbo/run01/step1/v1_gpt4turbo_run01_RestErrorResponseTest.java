
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

public class v1_gpt4turbo_run01_RestErrorResponseTest {

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
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer")
            .queryParam("name", "NonExistentUser")
        .when()
            .get()
        .then()
            .statusCode(404);
    }

    @Test
    public void testAddItemToCartUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .contentType("application/json")
            .body("{\"productId\": \"1\", \"quantity\": \"2\"}")
            .queryParam("name", "UnauthorizedUser")
        .when()
            .put()
        .then()
            .statusCode(401);
    }

    @Test
    public void testClearCartNoContent() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .queryParam("name", "EmptyCartUser")
        .when()
            .delete()
        .then()
            .statusCode(204);
    }

    @Test
    public void testSetDeliveryUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/delivery")
            .queryParam("included", true)
            .queryParam("name", "UnauthorizedUser")
        .when()
            .put()
        .then()
            .statusCode(401);
    }

    @Test
    public void testPayByCardNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/pay")
            .contentType("application/json")
            .body("{\"ccNumber\": \"4111111111111111\"}")
            .queryParam("name", "NonExistentUser")
        .when()
            .post()
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetContactsForbidden() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/contacts")
            .queryParam("name", "UnauthorizedUser")
        .when()
            .get()
        .then()
            .statusCode(403);
    }

    @Test
    public void testUpdateContactsNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/contacts")
            .contentType("application/json")
            .body("{\"address\": \"New Address\", \"phone\": \"+7 999 999 9999\"}")
            .queryParam("name", "NonExistentUser")
        .when()
            .put()
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrdersUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders")
            .queryParam("name", "UnauthorizedUser")
        .when()
            .get()
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetProductNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/products")
            .queryParam("productId", "9999")
        .when()
            .get()
        .then()
            .statusCode(404);
    }

    @Test
    public void testRegisterUserCreated() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/register")
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
        .when()
            .post()
        .then()
            .statusCode(201)
            .body("name", is("Ivan Petrov"));
    }
}
