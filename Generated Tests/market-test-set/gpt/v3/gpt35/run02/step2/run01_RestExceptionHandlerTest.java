
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
    public void testHandleOtherExceptions_InternalServerError() {
        Exception e = new Exception("Internal Server Error");
        WebRequest request = null;
        // RestErrorResponse response = new RestExceptionHandler(new MessageSource()).otherExceptions(e, request);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    public void testHandleAccessDeniedException_Unauthorized() {
        AccessDeniedException e = new AccessDeniedException("Unauthorized Access");
        WebRequest request = null;
        // RestErrorResponse response = new RestExceptionHandler(new MessageSource()).accessDeniedException(e, request);
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void testHandleUnknownEntityException_NotFound() {
        UnknownEntityException e = new UnknownEntityException("Entity not found", "User", List.of(new FieldError("User", "id", "User ID not found")));
        WebRequest request = null;
        // RestErrorResponse response = new RestExceptionHandler(new MessageSource()).unknownEntityException(e, request);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testHandleCustomNotValidException_NotAcceptable() {
        CustomNotValidException e = new CustomNotValidException("Validation failed", "User", List.of(new FieldError("User", "email", "Invalid email format")));
        WebRequest request = null;
        // RestErrorResponse response = new RestExceptionHandler(new MessageSource()).customNotValidException(e, request);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }

    @Test
    public void testProcessValidationError_NotAcceptable() {
        MethodArgumentNotValidException e = new MethodArgumentNotValidException(null, new BindingResult() {
            @Override
            public String getObjectName() {
                return "User";
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return List.of(new FieldError("User", "name", "Name is required"));
            }

            // Other methods implementations

        }, null);
        WebRequest request = null;
        // RestErrorResponse response = new RestExceptionHandler(new MessageSource()).processValidationError(e, request);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }
}
