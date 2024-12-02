
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
import org.evomaster.client.java.controller.expectation.ExpectationHandler;
import io.restassured.path.json.JsonPath;
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetCustomerUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetCustomerNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "NonExistentUser")
            .when()
            .get("/customer")
            .then()
            .statusCode(404);
    }

    @Test
    public void testAddToCartInvalidProduct() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"productId\": 999999, \"quantity\": 1}")
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    public void testPayByCardInvalidCard() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"ccNumber\": \"1234\"}")
            .when()
            .post("/customer/cart/pay")
            .then()
            .statusCode(400);
    }

    @Test
    public void testGetOrdersUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/orders")
            .then()
            .statusCode(401);
    }

    @Test
    public void testCreateCustomerInvalidEmail() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"invalidEmail\",\"password\":\"validPass1\",\"name\":\"Valid Name\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Valid Address\"}")
            .when()
            .post("/register")
            .then()
            .statusCode(400);
    }

    @Test
    public void testCreateCustomerSuccess() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .when()
            .post("/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testGetCartUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetCartNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "NonExistentUser")
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    public void testClearCartUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testSetDeliveryInvalidQuery() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(400);
    }
}
