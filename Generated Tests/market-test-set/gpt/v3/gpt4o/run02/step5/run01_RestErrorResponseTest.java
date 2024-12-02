
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
    public void testGetCustomerNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "nonexistent")
        .when()
            .get("/customer")
        .then()
            .statusCode(401); // Unauthorized
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
    public void testAddItemToCartInvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "ivan.petrov@yandex.ru")
            .body("{ \"productId\": null, \"quantity\": 1 }")
            .contentType("application/json")
        .when()
            .put("/customer/cart")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSetDeliveryInvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "ivan.petrov@yandex.ru")
            .queryParam("included", "invalid")
        .when()
            .put("/customer/cart/delivery")
        .then()
            .statusCode(500);
    }

    @Test
    public void testPayByCardInvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "ivan.petrov@yandex.ru")
            .body("{ \"ccNumber\": \"invalid\" }")
            .contentType("application/json")
        .when()
            .post("/customer/cart/pay")
        .then()
            .statusCode(406); // Not Acceptable due to validation error
    }

    @Test
    public void testCreateCustomerInvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .body("{ \"email\": \"invalid\", \"password\": \"short\", \"name\": \"Invalid Name!\", \"phone\": \"123\", \"address\": \"#Invalid\" }")
            .contentType("application/json")
        .when()
            .post("/register")
        .then()
            .statusCode(406); // Not Acceptable due to validation error
    }

    @Test
    public void testGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0)))
            .body("[0]", hasKey("productId"))
            .body("[0]", hasKey("name"))
            .body("[0]", hasKey("price"))
            .body("[0]", hasKey("volume"))
            .body("[0]", hasKey("alcohol"))
            .body("[0]", hasKey("age"))
            .body("[0]", hasKey("available"))
            .body("[0]", hasKey("description"))
            .body("[0]", hasKey("distillery"));
    }

    @Test
    public void testGetProductNotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 9999)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(404);
    }
}
