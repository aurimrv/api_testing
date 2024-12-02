
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_CustomerRestControllerTest {
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
    public void testCreateCustomer() {
        String payload = "{ \"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\" }";
        
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testCreateCustomerEmailExists() {
        String payload = "{ \"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\" }";

        // Create the user for the first time
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Try to create the same user again
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(409); // Assuming 409 Conflict for EmailExistsException
    }

    @Test
    public void testGetCustomerUnauthorized() {
        given()
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetCustomer() {
        String payload = "{ \"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\" }";

        // Register the user
        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Get the user
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testInternalServerError() {
        // Assuming there's an endpoint that can trigger a 500 error for testing
        given()
        .when()
            .get(baseUrlOfSut + "/trigger500")
        .then()
            .statusCode(500);
    }
}
