
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class v3_gpt4turbo_run02_RestErrorResponseTest {

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
    public void testGetCustomerWithInvalidName() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "")
            .when()
            .get("/customer")
            .then()
            .statusCode(401)
            .body("message", containsString(""));
    }

    @Test
    public void testAddItemWithInvalidProductId() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"productId\": -1, \"quantity\": 5}")
            .queryParam("name", "testCart")
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(406)
            .body("fieldErrors[0].message", containsString("Value shall be a positive number"));
    }

    @Test
    public void testClearCartWithInvalidCartName() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "")
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(401)
            .body("message", containsString("Acesso negado"));
    }

    @Test
    public void testSetDeliveryWithInvalidName() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .queryParam("included", true)
            .queryParam("name", "")
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(401)
            .body("message", containsString("Acesso negado"));
    }
}
