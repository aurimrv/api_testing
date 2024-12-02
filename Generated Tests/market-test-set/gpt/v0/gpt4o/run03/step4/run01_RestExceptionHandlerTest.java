
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

public class run01_RestExceptionHandlerTest {

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
    public void testUnauthorizedAccess() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer")
            .then()
            .statusCode(401); // Unauthorized
    }

    @Test
    public void testUnknownEntityException() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/orders/99999") // Assuming 99999 is a non-existent orderId
            .then()
            .statusCode(404); // Not Found
    }

    @Test
    public void testCustomNotValidException() {
        String invalidContact = "{ \"phone\": \"invalid-phone-number\", \"address\": \"Invalid Address\" }";
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(invalidContact)
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(406); // Not Acceptable
    }

    @Test
    public void testProcessValidationError() {
        String invalidUser = "{ \"email\": \"invalid-email\", \"password\": \"short\", \"name\": \"Valid Name\", \"phone\": \"+1 123 456 78 90\", \"address\": \"Valid Address\" }";
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(invalidUser)
            .when()
            .post("/register")
            .then()
            .statusCode(406); // Not Acceptable
    }

    @Test
    public void testOtherExceptions() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/nonexistent-endpoint")
            .then()
            .statusCode(500); // Internal Server Error
    }
}
