
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

public class v1_gpt4o_run03_CartRestControllerTest {

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
        String token = login("ivan.petrov@yandex.ru", "petrov");
        ValidatableResponse response = given()
            .auth().oauth2(token)
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
        
        response.body("user", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testAddItem() {
        String token = login("ivan.petrov@yandex.ru", "petrov");
        Map<String, Object> item = Map.of(
            "productId", 1,
            "quantity", 2
        );

        ValidatableResponse response = given()
            .auth().oauth2(token)
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);

        response.body("totalItems", equalTo(2));
    }

    @Test
    public void testClearCart() {
        String token = login("ivan.petrov@yandex.ru", "petrov");

        given()
            .auth().oauth2(token)
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);

        ValidatableResponse response = given()
            .auth().oauth2(token)
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);

        response.body("totalItems", equalTo(0));
    }

    @Test
    public void testSetDelivery() {
        String token = login("ivan.petrov@yandex.ru", "petrov");

        ValidatableResponse response = given()
            .auth().oauth2(token)
            .contentType("application/json")
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200);

        response.body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testPayByCard() {
        String token = login("ivan.petrov@yandex.ru", "petrov");
        Map<String, String> card = Map.of(
            "ccNumber", "1234-5678-9876-5432"
        );

        ValidatableResponse response = given()
            .auth().oauth2(token)
            .contentType("application/json")
            .body(card)
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201);

        response.body("userAccount", equalTo("ivan.petrov@yandex.ru"));
    }

    private String login(String email, String password) {
        Map<String, String> credentials = Map.of("email", email, "password", password);
        return given()
            .contentType("application/json")
            .body(credentials)
            .when()
            .post(baseUrlOfSut + "/authenticate")
            .then()
            .statusCode(200)
            .extract()
            .path("token");
    }
}
