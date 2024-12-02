
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
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("cartItems", notNullValue())
            .body("deliveryCost", is(0))
            .body("deliveryIncluded", is(false))
            .body("empty", is(true))
            .body("productsCost", is(0.0))
            .body("totalCost", is(0.0))
            .body("totalItems", is(0))
            .body("user", is("ivan.petrov@yandex.ru"));

        response.assertThat().body(matchesJsonSchemaInClasspath("schemas/CartDTO.json"));
    }

    @Test
    public void testAddItem() {
        String item = "{ \"productId\": 1, \"quantity\": 1 }";
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("cartItems.size()", is(1))
            .body("cartItems[0].productId", is(1))
            .body("cartItems[0].quantity", is(1));

        response.assertThat().body(matchesJsonSchemaInClasspath("schemas/CartDTO.json"));
    }

    @Test
    public void testClearCart() {
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("cartItems", empty())
            .body("totalItems", is(0));

        response.assertThat().body(matchesJsonSchemaInClasspath("schemas/CartDTO.json"));
    }

    @Test
    public void testSetDelivery() {
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(200)
            .body("deliveryIncluded", is(true));

        response.assertThat().body(matchesJsonSchemaInClasspath("schemas/CartDTO.json"));
    }

    @Test
    public void testPayByCard() {
        String card = "{ \"ccNumber\": \"4111111111111111\" }";
        ValidatableResponse response = given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(201)
            .body("userAccount", is("ivan.petrov@yandex.ru"))
            .body("payed", is(true));

        response.assertThat().body(matchesJsonSchemaInClasspath("schemas/OrderDTO.json"));
    }

    @Test
    public void testAddInvalidItem() {
        String item = "{ \"productId\": -1, \"quantity\": 1 }";
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(404);
    }

    @Test
    public void testPayWithEmptyCart() {
        String card = "{ \"ccNumber\": \"4111111111111111\" }";
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(500);
    }
}
