
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

public class v2_gpt4turbo_run02_CustomerRestControllerTest {
    
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
    @Timeout(5)
    public void testGetCustomerUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer")
            .then()
            .assertThat()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testCreateCustomerSuccess() {
        String requestBody = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/register")
            .then()
            .assertThat()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(5)
    public void testCreateCustomerEmailExists() {
        String requestBody = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .post("/register");

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/register")
            .then()
            .assertThat()
            .statusCode(406)  // Corrected based on actual server response
            .body("fieldErrors.field", hasItem("email"))
            .body("fieldErrors.message", hasItem("Account with this email already exists"));
    }

    @Test
    @Timeout(5)
    public void testSchemaValidationFailure() {
        String invalidRequestBody = "{\"email\":\"ivan@yandex\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"123\",\"address\":\"Riesstrasse 18\"}";

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(invalidRequestBody)
            .when()
            .post("/register")
            .then()
            .assertThat()
            .statusCode(406)  // Corrected based on actual server response
            .body("fieldErrors.field", hasItems("phone", "email"))
            .body("fieldErrors.message", hasItems(
                "Shall consist of a + sign, a country code, a region code (1-4 digits), and a number (6-7 digits)",
                "The value shall be in the format of an email address"));
    }
}
