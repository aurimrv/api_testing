
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

public class v2_gpt4o_run02_CustomerRestControllerTest {

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
    @Timeout(60)
    public void testGetCustomerUnauthorized() {
        given()
            .auth().none()
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testRegisterCustomer() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        Map<String, String> user = Map.of(
            "email", email,
            "password", password,
            "name", name,
            "phone", phone,
            "address", address
        );

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(email))
            .body("name", equalTo(name))
            .body("phone", equalTo(phone))
            .body("address", equalTo(address));
    }

    @Test
    @Timeout(60)
    public void testRegisterCustomerDuplicateEmail() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        Map<String, String> user = Map.of(
            "email", email,
            "password", password,
            "name", name,
            "phone", phone,
            "address", address
        );

        // Register the first user
        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Attempt to register the second user with the same email
        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(406) // Corrected based on error message
            .body("fieldErrors[0].field", equalTo("email"))
            .body("fieldErrors[0].message", equalTo("Account with this email already exists"));
    }

    @Test
    @Timeout(60)
    public void testGetCustomerNotFound() {
        given()
            .auth().preemptive().basic("nonexistent.email@example.com", "password")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401); // Corrected based on error message
    }

    @Test
    @Timeout(60)
    public void testSchemaValidationOnGetCustomer() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        Map<String, String> user = Map.of(
            "email", email,
            "password", password,
            "name", name,
            "phone", phone,
            "address", address
        );

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        given()
            .auth().preemptive().basic(email, password)
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo(email))
            .body("name", equalTo(name))
            .body("phone", equalTo(phone))
            .body("address", equalTo(address));
    }
}
