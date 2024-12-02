
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
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
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
    public void testGetCartUnauthorized() {
        given()
            .auth().none()
        .when()
            .get(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetCart() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200)
            .body("user", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testAddItemToCart() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"productId\":1, \"quantity\":2}")
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200)
            .body("totalItems", equalTo(2));
    }

    @Test
    public void testAddItemToCartInvalidProduct() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"productId\":9999, \"quantity\":2}")
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(404);
    }

    @Test
    public void testClearCart() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .delete(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200)
            .body("totalItems", equalTo(0));
    }

    @Test
    public void testSetDelivery() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .put(baseUrlOfSut + "/customer/cart/delivery?included=true")
        .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testPayByCardEmptyCart() {
        // Assuming user is logged in
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"ccNumber\":\"4111111111111111\"}")
        .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
        .then()
            .statusCode(500); // Empty cart should cause a server error
    }

    @Test
    public void testPayByCard() {
        // Assuming user is logged in
        // Add an item to cart first
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"productId\":1, \"quantity\":2}")
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200);

        // Then try to pay
        ValidatableResponse response = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"ccNumber\":\"4111111111111111\"}")
        .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
        .then()
            .statusCode(201)
            .body("payed", equalTo(true));
    }
}
