
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

public class run01_RestErrorResponseTest {

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
    @Timeout(60)
    public void testGetCustomerNotFound() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .when().get("/customer?name=nonexistent")
               .then().statusCode(404)
               .body("message", equalTo("Customer not found"));
    }

    @Test
    @Timeout(60)
    public void testAddItemToCartInvalidProduct() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .contentType("application/json")
               .body("{\"productId\":9999,\"quantity\":1}")
               .when().put("/customer/cart?name=Ivan")
               .then().statusCode(404)
               .body("message", equalTo("Product not found"));
    }

    @Test
    @Timeout(60)
    public void testGetCartUnauthorized() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/cart")
               .then().statusCode(401)
               .body("message", equalTo("Unauthorized"));
    }

    @Test
    @Timeout(60)
    public void testCreateCustomer() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post("/register")
               .then().statusCode(201)
               .body("email", equalTo("ivan.petrov@yandex.ru"))
               .body("name", equalTo("Ivan Petrov"))
               .body("phone", equalTo("+7 123 456 78 90"))
               .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(60)
    public void testCreateCustomerInvalidEmail() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"email\":\"invalid-email\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post("/register")
               .then().statusCode(406)
               .body("message", containsString("The value shall be in the format of an email address"));
    }

    @Test
    @Timeout(60)
    public void testGetProductNotFound() {
        given().baseUri(baseUrlOfSut)
               .when().get("/products/9999")
               .then().statusCode(404)
               .body("message", equalTo("Requested entity doesn't exist"));
    }

    @Test
    @Timeout(60)
    public void testGetProducts() {
        given().baseUri(baseUrlOfSut)
               .when().get("/products")
               .then().statusCode(200)
               .body("$", not(empty()))
               .body("alcohol", everyItem(greaterThan(0.0)))
               .body("price", everyItem(greaterThan(0.0)))
               .body("volume", everyItem(greaterThan(0)));
    }

    @Test
    @Timeout(60)
    public void testGetOrderNotFound() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .when().get("/customer/orders/9999")
               .then().statusCode(404)
               .body("message", equalTo("Order not found"));
    }

    @Test
    @Timeout(60)
    public void testPayByCardInvalidCardNumber() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .contentType("application/json")
               .body("{\"ccNumber\":\"1234\"}")
               .when().post("/customer/cart/pay?name=Ivan")
               .then().statusCode(406)
               .body("message", containsString("Card number shall consist of 13-16 digits"));
    }

    @Test
    @Timeout(60)
    public void testClearCart() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .when().delete("/customer/cart?name=Ivan")
               .then().statusCode(200)
               .body("totalItems", equalTo(0));
    }

    @Test
    @Timeout(60)
    public void testUpdateContacts() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .contentType("application/json")
               .body("{\"address\":\"Riesstrasse 19\",\"phone\":\"+7 987 654 32 10\"}")
               .when().put("/customer/contacts?name=Ivan")
               .then().statusCode(200)
               .body("address", equalTo("Riesstrasse 19"))
               .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    @Timeout(60)
    public void testSetDeliveryIncluded() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .contentType("application/json")
               .when().put("/customer/cart/delivery?included=true&name=Ivan")
               .then().statusCode(200)
               .body("deliveryIncluded", equalTo(true));
    }

    @Test
    @Timeout(60)
    public void testSetDeliveryExcluded() {
        given().baseUri(baseUrlOfSut)
               .auth().basic("user", "password")
               .contentType("application/json")
               .when().put("/customer/cart/delivery?included=false&name=Ivan")
               .then().statusCode(200)
               .body("deliveryIncluded", equalTo(false));
    }
}
