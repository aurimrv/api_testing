
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

public class run01_ProductsRestControllerTest {

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
    public void testGetAllProducts() {
        given()
            .when()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200)
            .contentType("application/json")
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testGetProductById() {
        // Insert a product to test retrieval
        InsertionDto productInsertion = sql().insertInto("PRODUCT")
            .d("ID", "1")
            .d("NAME", "Product1")
            .d("PRICE", "100.0")
            .d("VOLUME", 500)
            .d("ALCOHOL", 40.0)
            .d("AVAILABLE", true)
            .execute();

        InsertionResultsDto results = controller.insertIntoDatabase(productInsertion);
        assertNotNull(results);

        given()
            .pathParam("productId", 1)
            .when()
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(200)
            .contentType("application/json")
            .body("productId", equalTo(1))
            .body("name", equalTo("Product1"))
            .body("price", equalTo(100.0f))
            .body("volume", equalTo(500))
            .body("alcohol", equalTo(40.0f))
            .body("available", equalTo(true));
    }

    @Test
    public void testGetNonExistentProduct() {
        given()
            .pathParam("productId", 9999)
            .when()
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(404);
    }
}
