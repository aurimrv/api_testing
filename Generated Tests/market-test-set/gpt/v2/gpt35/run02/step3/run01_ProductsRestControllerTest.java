
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testInvalidProductIdReturns404() {
        given()
            .when()
            .get(baseUrlOfSut + "/products/999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetAllProducts() {
        given()
            .when()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200);
    }

    @Test
    public void testInternalServerErrorForInvalidInput() {
        given()
            .when()
            .get(baseUrlOfSut + "/products/invalidInput")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForProductDTO() {
        given()
            .when()
            .get(baseUrlOfSut + "/products")
            .then()
            .body(matchesJsonSchema(new File("src/test/resources/product-schema.json")));
    }

    @Test
    public void testCreateProduct() {
        InsertionResultsDto results = given()
            .contentType("application/json")
            .body(new InsertionDto("New Product", 10.0))
            .post(baseUrlOfSut + "/products")
            .then()
            .statusCode(201)
            .extract()
            .as(InsertionResultsDto.class);

        assertNotNull(results);
        assertTrue(results.isSuccess());
        assertNotNull(results.getGeneratedKeys());
    }

}
