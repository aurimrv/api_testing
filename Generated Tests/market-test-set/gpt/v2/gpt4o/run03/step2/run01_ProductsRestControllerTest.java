
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
        given().baseUri(baseUrlOfSut)
            .when().get("/products")
            .then().statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(0)))
            .body("alcohol", everyItem(is(notNullValue())))
            .body("price", everyItem(is(notNullValue())))
            .body("volume", everyItem(is(notNullValue())));
    }

    @Test
    public void testGetProductById() {
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
            .when().get("/products/{productId}")
            .then().statusCode(200)
            .body("productId", equalTo(1))
            .body("alcohol", is(notNullValue()))
            .body("price", is(notNullValue()))
            .body("volume", is(notNullValue()));
    }

    @Test
    public void testGetNonExistingProduct() {
        given().baseUri(baseUrlOfSut)
            .pathParam("productId", 9999)
            .when().get("/products/{productId}")
            .then().statusCode(404)
            .body("error", containsString("Unknown entity"));
    }

    @Test
    public void testInternalServerError() {
        // Simulate a scenario that causes a server error (e.g., invalid database state)
        // This is a placeholder for an actual implementation that triggers a 500 error.
        given().baseUri(baseUrlOfSut)
            .when().get("/products/trigger500")
            .then().statusCode(500);
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Assuming there is a business rule for product creation, modification, or deletion
        // This is a placeholder for an actual implementation.
        // Create a new product
        Map<String, Object> newProduct = Map.of("name", "Test Product", "alcohol", 40, "price", 19.99, "volume", 750);
        given().baseUri(baseUrlOfSut)
            .body(newProduct)
            .when().post("/products")
            .then().statusCode(201)
            .body("name", equalTo("Test Product"))
            .body("alcohol", equalTo(40))
            .body("price", equalTo(19.99))
            .body("volume", equalTo(750));

        // Modify the product
        Map<String, Object> updatedProduct = Map.of("name", "Updated Product", "alcohol", 50, "price", 29.99, "volume", 700);
        given().baseUri(baseUrlOfSut)
            .body(updatedProduct)
            .when().put("/products/{productId}", 1)
            .then().statusCode(200)
            .body("name", equalTo("Updated Product"))
            .body("alcohol", equalTo(50))
            .body("price", equalTo(29.99))
            .body("volume", equalTo(700));

        // Delete the product
        given().baseUri(baseUrlOfSut)
            .when().delete("/products/{productId}", 1)
            .then().statusCode(204);
    }
}
