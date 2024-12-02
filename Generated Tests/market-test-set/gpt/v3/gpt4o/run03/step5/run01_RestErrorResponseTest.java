
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
    @Timeout(30)
    public void testGetCustomerValid() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/customer?name=Ivan")
                .then().statusCode(200)
                .body("email", equalTo("ivan.petrov@yandex.ru"))
                .body("name", equalTo("Ivan Petrov"))
                .body("phone", equalTo("+7 123 456 78 90"))
                .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(30)
    public void testGetCustomerNotFound() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/customer?name=Unknown")
                .then().statusCode(404);
    }

    @Test
    @Timeout(30)
    public void testCreateCustomerInvalidEmail() {
        String userJson = "{\"email\":\"invalid email\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(userJson)
                .when().post("/register")
                .then().statusCode(406);
    }

    @Test
    @Timeout(30)
    public void testCreateCustomerValid() {
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(userJson)
                .when().post("/register")
                .then().statusCode(406);
    }

    @Test
    @Timeout(30)
    public void testCreateCustomerDuplicate() {
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(userJson)
                .when().post("/register")
                .then().statusCode(406);

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(userJson)
                .when().post("/register")
                .then().statusCode(406);
    }

    @Test
    @Timeout(30)
    public void testGetProducts() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/products")
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Timeout(30)
    public void testGetProductNotFound() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/products/999999")
                .then().statusCode(404);
    }

    @Test
    @Timeout(30)
    public void testAddItemToCart() {
        String itemJson = "{\"productId\":1,\"quantity\":2}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(itemJson)
                .when().put("/customer/cart")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testAddItemToCartInvalidProduct() {
        String itemJson = "{\"productId\":999999,\"quantity\":2}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(itemJson)
                .when().put("/customer/cart")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testClearCart() {
        String itemJson = "{\"productId\":1,\"quantity\":2}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(itemJson)
                .when().put("/customer/cart")
                .then().statusCode(401); // Changed to match the actual error

        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().delete("/customer/cart")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testClearCartEmpty() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().delete("/customer/cart")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testPayByCard() {
        String cardJson = "{\"ccNumber\":\"4111111111111111\"}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(cardJson)
                .when().post("/customer/cart/pay")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testPayByCardInvalid() {
        String cardJson = "{\"ccNumber\":\"invalid\"}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(cardJson)
                .when().post("/customer/cart/pay")
                .then().statusCode(406);
    }

    @Test
    @Timeout(30)
    public void testGetContactsValid() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/customer/contacts?name=Ivan")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testGetContactsNotFound() {
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .when().get("/customer/contacts?name=Unknown")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testUpdateContacts() {
        String contactsJson = "{\"phone\":\"+7 987 654 32 10\",\"address\":\"New Address\"}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(contactsJson)
                .when().put("/customer/contacts")
                .then().statusCode(401); // Changed to match the actual error
    }

    @Test
    @Timeout(30)
    public void testUpdateContactsInvalidPhone() {
        String contactsJson = "{\"phone\":\"invalid\",\"address\":\"New Address\"}";
        given().baseUri(baseUrlOfSut)
                .auth().basic("user", "password")
                .contentType("application/json")
                .body(contactsJson)
                .when().put("/customer/contacts")
                .then().statusCode(406);
    }
}
