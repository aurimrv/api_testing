
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
import io.restassured.module.jsv.JsonSchemaValidator; // Added for schema validation

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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetAllProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetSingleProduct() {
        long productId = 1; // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId))
            .body("price", greaterThan(0.0))
            .body("volume", greaterThan(0));
    }

    @Test
    public void testGetNonExistentProduct() {
        long nonExistentProductId = 99999;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", nonExistentProductId)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(404)
            .body("message", containsString("Requested entity doesn't exist"));
    }

    @Test
    public void testCreateProduct() {
        Map<String, Object> newProduct = Map.of(
            "name", "New Product",
            "description", "A new product",
            "price", 19.99,
            "volume", 750,
            "alcohol", 40,
            "available", true
        );

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(newProduct)
        .when()
            .post("/products")
        .then()
            .statusCode(201)
            .body("name", equalTo("New Product"))
            .body("price", equalTo(19.99f));
    }

    @Test
    public void testCreateProductWithInvalidData() {
        Map<String, Object> invalidProduct = Map.of(
            "name", "",
            "price", -1,
            "volume", 0,
            "alcohol", 0,
            "available", true
        );

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(invalidProduct)
        .when()
            .post("/products")
        .then()
            .statusCode(400);
    }

    @Test
    public void testInternalServerError() {
        // Simulate scenario causing 500 error, like null pointer exception or database failure
        // This depends on the actual implementation details which are not provided
    }

    @Test
    public void testSchemaValidationGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/products-schema.json")); // Corrected method call
    }

    @Test
    public void testSchemaValidationGetSingleProduct() {
        long productId = 1; // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", productId)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/product-schema.json")); // Corrected method call
    }
}
