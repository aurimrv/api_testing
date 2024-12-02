
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

public class v2_gpt4o_run02_OrdersRestControllerTest {

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
    public void testGetOrders() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("totalCost", everyItem(isA(Double.class)));
    }

    @Test
    @Timeout(60)
    public void testGetOrder() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/1")
            .then().statusCode(200)
            .body("id", equalTo(1))
            .body("totalCost", isA(Double.class));
    }

    @Test
    @Timeout(60)
    public void testGetOrderNotFound() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/9999")
            .then().statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetOrdersUnauthorized() {
        given().when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrderInvalidId() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/invalid")
            .then().statusCode(400);
    }

    @Test
    @Timeout(60)
    public void testGetOrderServerError() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/-1")
            .then().statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testSchemaValidationGetOrders() {
        ValidatableResponse response = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200);

        response.body("totalCost", everyItem(isA(Double.class)));
    }

    @Test
    @Timeout(60)
    public void testSchemaValidationGetOrder() {
        ValidatableResponse response = given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/1")
            .then().statusCode(200);

        response.body("id", equalTo(1));
        response.body("totalCost", isA(Double.class));
    }

    @Test
    @Timeout(60)
    public void testBusinessRuleGetOrders() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200)
            .body("$.size()", greaterThan(0));
    }

    @Test
    @Timeout(60)
    public void testBusinessRuleGetOrder() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/1")
            .then().statusCode(200)
            .body("id", equalTo(1));
    }

    @Test
    @Timeout(60)
    public void testInvalidEndpoint() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/orders/invalidEndpoint")
            .then().statusCode(400);
    }
}
