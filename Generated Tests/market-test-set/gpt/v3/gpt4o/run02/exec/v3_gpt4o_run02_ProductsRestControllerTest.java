
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v3_gpt4o_run02_ProductsRestControllerTest {

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
    public void testGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("[0].productId", notNullValue())
            .body("[0].name", notNullValue())
            .body("[0].price", notNullValue());
    }

    @Test
    public void testGetProductById() {
        long productId = 1; // Assuming there's a product with ID 1
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId))
            .body("name", notNullValue())
            .body("price", notNullValue());
    }

    @Test
    public void testGetProductByInvalidId() {
        long invalidProductId = 9999; // Assuming this ID doesn't exist
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", invalidProductId)
        .then()
            .statusCode(404);
    }

    @Test
    public void testInternalServerErrorSimulation() {
        // Simulate internal server error by sending malformed request
        given()
            .baseUri(baseUrlOfSut)
            .body("{invalidJson}")
            .header("Content-Type", "application/json")
        .when()
            .post("/products")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("[0].productId", instanceOf(Integer.class))
            .body("[0].name", instanceOf(String.class))
            .body("[0].price", instanceOf(Double.class));
    }

    @Test
    public void testSchemaValidationForGetProductById() {
        long productId = 1; // Assuming there's a product with ID 1
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products/{productId}", productId)
        .then()
            .statusCode(200)
            .body("productId", instanceOf(Integer.class))
            .body("name", instanceOf(String.class))
            .body("price", instanceOf(Double.class));
    }
}
