
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
    public void testGetCustomer() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer")
               .then().statusCode(200);
    }

    @Test
    public void testGetCustomerCart() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/cart")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer/cart")
               .then().statusCode(200);
    }

    @Test
    public void testAddItemToCart() {
        given().baseUri(baseUrlOfSut)
               .body("{ \"productId\": 1, \"quantity\": 2 }")
               .contentType("application/json")
               .when().put("/customer/cart")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .body("{ \"productId\": 1, \"quantity\": 2 }")
               .contentType("application/json")
               .when().put("/customer/cart")
               .then().statusCode(200);
    }

    @Test
    public void testClearCart() {
        given().baseUri(baseUrlOfSut)
               .when().delete("/customer/cart")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().delete("/customer/cart")
               .then().statusCode(200);
    }

    @Test
    public void testSetDelivery() {
        given().baseUri(baseUrlOfSut)
               .queryParam("included", true)
               .when().put("/customer/cart/delivery")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .queryParam("included", true)
               .when().put("/customer/cart/delivery")
               .then().statusCode(200);
    }

    @Test
    public void testPayByCard() {
        given().baseUri(baseUrlOfSut)
               .body("{ \"ccNumber\": \"4111111111111111\" }")
               .contentType("application/json")
               .when().post("/customer/cart/pay")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .body("{ \"ccNumber\": \"4111111111111111\" }")
               .contentType("application/json")
               .when().post("/customer/cart/pay")
               .then().statusCode(201);
    }

    @Test
    public void testGetContacts() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/contacts")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer/contacts")
               .then().statusCode(200);
    }

    @Test
    public void testUpdateContacts() {
        given().baseUri(baseUrlOfSut)
               .body("{ \"address\": \"Riesstrasse 18\", \"phone\": \"+7 123 456 78 90\" }")
               .contentType("application/json")
               .when().put("/customer/contacts")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .body("{ \"address\": \"Riesstrasse 18\", \"phone\": \"+7 123 456 78 90\" }")
               .contentType("application/json")
               .when().put("/customer/contacts")
               .then().statusCode(200);
    }

    @Test
    public void testGetOrders() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/orders")
               .then().statusCode(401);

        // Add a user and test
        InsertionDto userDto = new InsertionDto("USER", 
            Arrays.asList("email", "password", "name", "phone", "address"), 
            Arrays.asList("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18"));
        controller.insertData(userDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer/orders")
               .then().statusCode(200);
    }

    @Test
    public void testGetProduct() {
        given().baseUri(baseUrlOfSut)
               .pathParam("productId", 1)
               .when().get("/products/{productId}")
               .then().statusCode(401);

        // Add a product and test
        InsertionDto productDto = new InsertionDto("PRODUCT", 
            Arrays.asList("productId", "name", "price", "volume", "alcohol", "available"), 
            Arrays.asList(1, "Whiskey", 50.0, 750, 40.0, true));
        controller.insertData(productDto);

        given().baseUri(baseUrlOfSut).auth().basic("ivan.petrov@yandex.ru", "petrov")
               .pathParam("productId", 1)
               .when().get("/products/{productId}")
               .then().statusCode(200);
    }
}
