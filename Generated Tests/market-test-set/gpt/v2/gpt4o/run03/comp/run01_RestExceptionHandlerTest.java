
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
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
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
    public void testInternalServerError() {
        given().contentType("application/json")
            .body("{\"invalid\":\"data\"}")
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(500)
            .body("message", containsString("Internal Server Error"));
    }

    @Test
    public void testUnauthorizedAccess() {
        given().contentType("application/json")
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401)
            .body("message", containsString("Unauthorized"));
    }

    @Test
    public void testNotFound() {
        given().contentType("application/json")
            .get(baseUrlOfSut + "/customer/orders/999")
            .then()
            .statusCode(404)
            .body("message", containsString("Not Found"));
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().contentType("application/json")
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(200);
        
        response.body("email", matchesPattern("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$"))
                .body("name", matchesPattern("^[\\pL '-]+$"))
                .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));
    }

    @Test
    public void testCreateCustomer() {
        ValidatableResponse response = given().contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);
        
        response.body("email", equalTo("ivan.petrov@yandex.ru"))
                .body("name", equalTo("Ivan Petrov"))
                .body("phone", equalTo("+7 123 456 78 90"))
                .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testAddItemToCart() {
        given().contentType("application/json")
            .body("{\"productId\":1,\"quantity\":2}")
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("cartItems.productId", hasItem(1))
            .body("cartItems.quantity", hasItem(2));
    }

    @Test
    public void testClearCart() {
        given().contentType("application/json")
            .body("{\"productId\":1,\"quantity\":2}")
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);

        given().contentType("application/json")
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(204);
    }
}
