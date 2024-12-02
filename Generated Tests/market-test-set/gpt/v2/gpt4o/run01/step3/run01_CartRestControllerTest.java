
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
    public void testGetCart() {
        ValidatableResponse res = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        res.assertThat().body("deliveryCost", isA(Integer.class));
        res.assertThat().body("deliveryIncluded", isA(Boolean.class));
        res.assertThat().body("empty", isA(Boolean.class));
        res.assertThat().body("productsCost", isA(Double.class));
        res.assertThat().body("totalCost", isA(Double.class));
        res.assertThat().body("totalItems", isA(Integer.class));
        res.assertThat().body("user", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testAddItemToCart() {
        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);

        ValidatableResponse res = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        res.assertThat().body("totalItems", equalTo(1));
    }

    @Test
    public void testClearCart() {
        ValidatableResponse res = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        res.assertThat().body("totalItems", equalTo(0));
    }

    @Test
    public void testSetDelivery() {
        ValidatableResponse res = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .param("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(200);

        res.assertThat().body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testPayByCard() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCcNumber("4111111111111111");

        ValidatableResponse res = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(201);

        res.assertThat().body("payed", equalTo(true));
    }

    @Test
    public void testInvalidProductId() {
        CartItemDTO item = new CartItemDTO();
        item.setProductId(-1L);
        item.setQuantity(2);

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(500);
    }

    @Test
    public void testInvalidCreditCard() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCcNumber("invalid_card");

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(500);
    }
}
