
package market;

import org.evomaster.client.java.controller.SutHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrdersRestControllerTest {

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
                .jsonConfig(io.restassured.path.json.config.JsonConfig.jsonConfig().numberReturnType(io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE))
                .redirect(io.restassured.config.RedirectConfig.redirectConfig().followRedirects(false));
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
    public void testGetOrders() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    public void testGetOrder() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/orders/1")
            .then()
            .statusCode(404);
    }

}
