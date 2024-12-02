
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

public class run01_OrdersRestControllerTest {

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
    public void testGetOrders() {
        given().
            auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov").
        when().
            get(baseUrlOfSut + "/customer/orders").
        then().
            statusCode(200).
            body("size()", greaterThan(0));
    }

    @Test
    public void testGetOrderById() {
        given().
            auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov").
        when().
            get(baseUrlOfSut + "/customer/orders/1").
        then().
            statusCode(200).
            body("id", equalTo(1));
    }

    @Test
    public void testGetOrderByIdNotFound() {
        given().
            auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov").
        when().
            get(baseUrlOfSut + "/customer/orders/999").
        then().
            statusCode(404);
    }

    @Test
    public void testGetOrdersUnauthorized() {
        given().
        when().
            get(baseUrlOfSut + "/customer/orders").
        then().
            statusCode(401);
    }

    @Test
    public void testServerErrorSimulation() {
        given().
            auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov").
            header("X-Force-Error", "500").
        when().
            get(baseUrlOfSut + "/customer/orders").
        then().
            statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        given().
            auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov").
        when().
            get(baseUrlOfSut + "/customer/orders").
        then().
            statusCode(200).
            body(matchesJsonSchemaInClasspath("schemas/OrderDTO.json"));
    }
}
