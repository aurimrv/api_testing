
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetProducts() {
        given().get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    public void testGetProductById() {
        long productId = 1L; // Assuming 1L exists for this test
        given().get(baseUrlOfSut + "/products/" + productId)
            .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId));
    }

    @Test
    public void testGetProductByIdNotFound() {
        long productId = 999L; // Assuming 999L does not exist
        given().get(baseUrlOfSut + "/products/" + productId)
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(10)
    public void testInternalServerError() {
        // Simulate an internal server error by providing invalid input or forcing a failure scenario
        given().get(baseUrlOfSut + "/products/invalid")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForGetProducts() {
        given().get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200)
            .body("$", everyItem(hasKey("productId")))
            .body("$", everyItem(hasKey("name")))
            .body("$", everyItem(hasKey("price")))
            .body("$", everyItem(hasKey("volume")))
            .body("$", everyItem(hasKey("alcohol")));
    }

    @Test
    public void testSchemaValidationForGetProductById() {
        long productId = 1L; // Assuming 1L exists for this test
        given().get(baseUrlOfSut + "/products/" + productId)
            .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId))
            .body("$", hasKey("name"))
            .body("$", hasKey("price"))
            .body("$", hasKey("volume"))
            .body("$", hasKey("alcohol"));
    }

    @Test
    public void testBusinessRulesForGetProducts() {
        given().get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("findAll { it.price <= 0 }", empty());
    }

    @Test
    public void testBusinessRulesForGetProductById() {
        long productId = 1L; // Assuming 1L exists for this test
        given().get(baseUrlOfSut + "/products/" + productId)
            .then()
            .statusCode(200)
            .body("price", greaterThan(0.0))
            .body("name", matchesPattern("^[^#$%^&*()']*$"));
    }
}
