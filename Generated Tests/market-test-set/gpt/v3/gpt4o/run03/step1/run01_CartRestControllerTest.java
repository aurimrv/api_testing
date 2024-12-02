
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
import static org.hamcrest.Matchers.*;
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
    public void testGetCart() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("user", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testAddItemToCart() {
        CartItemDTOReq item = new CartItemDTOReq();
        item.setProductId(1L);
        item.setQuantity(2);

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", equalTo(1));
    }

    @Test
    public void testClearCart() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("empty", equalTo(true));
    }

    @Test
    public void testSetDelivery() {
        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testPayByCard() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCcNumber("1234 5678 9876 5432");

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201)
            .body("payed", equalTo(true));
    }

    @Test
    public void testGetCartUnauthorized() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItemToCartUnauthorized() {
        CartItemDTOReq item = new CartItemDTOReq();
        item.setProductId(1L);
        item.setQuantity(2);

        given()
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testClearCartUnauthorized() {
        given()
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testSetDeliveryUnauthorized() {
        given()
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(401);
    }

    @Test
    public void testPayByCardUnauthorized() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCcNumber("1234 5678 9876 5432");

        given()
            .contentType("application/json")
            .body(card)
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItemToCartInvalidProduct() {
        CartItemDTOReq item = new CartItemDTOReq();
        item.setProductId(999L);  // Assuming this product ID doesn't exist
        item.setQuantity(2);

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    public void testPayByCardEmptyCart() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCcNumber("1234 5678 9876 5432");

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(500);  // Assuming it throws EmptyCartException when cart is empty
    }

    @Test
    public void testAddItemToCartInvalidQuantity() {
        CartItemDTOReq item = new CartItemDTOReq();
        item.setProductId(1L);
        item.setQuantity(-1);

        given()
            .auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(400);  // Assuming it validates the quantity
    }
}
