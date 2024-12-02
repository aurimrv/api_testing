
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
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_RestErrorResponseTest {
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
    public void testRestErrorResponseWithEntityName() {
        String message = "Error occurred";
        String entityName = "User";
        String description = "Description of error";

        market.rest.exception.RestErrorResponse response = new market.rest.exception.RestErrorResponse(message, entityName, description);

        assertEquals(message, response.getMessage());
        assertEquals(entityName, response.getEntityName());
        assertEquals(description, response.getDescription());
        assertTrue(response.getFieldErrors().isEmpty());
    }

    @Test
    public void testRestErrorResponseWithoutEntityName() {
        String message = "Error occurred";
        String description = "Description of error";

        market.rest.exception.RestErrorResponse response = new market.rest.exception.RestErrorResponse(message, description);

        assertEquals(message, response.getMessage());
        assertNull(response.getEntityName());
        assertEquals(description, response.getDescription());
        assertTrue(response.getFieldErrors().isEmpty());
    }

    @Test
    public void testAddFieldError() {
        String message = "Error occurred";
        String entityName = "Product";
        String description = "Validation failed";
        String path = "name";
        String fieldMessage = "Name is required";

        market.rest.exception.RestErrorResponse response = new market.rest.exception.RestErrorResponse(message, entityName, description);
        response.addFieldError(path, fieldMessage);

        assertEquals(message, response.getMessage());
        assertEquals(entityName, response.getEntityName());
        assertEquals(description, response.getDescription());
        assertFalse(response.getFieldErrors().isEmpty());
        assertEquals(1, response.getFieldErrors().size());
        assertEquals(path, response.getFieldErrors().get(0).getPath());
        assertEquals(fieldMessage, response.getFieldErrors().get(0).getMessage());
    }
}
