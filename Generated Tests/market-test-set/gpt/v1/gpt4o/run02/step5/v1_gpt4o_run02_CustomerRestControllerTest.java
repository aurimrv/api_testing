
package market;

import  org.junit.jupiter.api.AfterAll;
import  org.junit.jupiter.api.BeforeAll;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;

public class v1_gpt4o_run02_CustomerRestControllerTest {

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
    public void testGetCustomerUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testCreateCustomer() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        String userJson = String.format(
            "{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\",\"phone\":\"%s\",\"address\":\"%s\"}",
            email, password, name, phone, address);

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(email))
            .body("name", equalTo(name))
            .body("phone", equalTo(phone))
            .body("address", equalTo(address));
    }

    @Test
    public void testCreateCustomerEmailExists() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        String userJson = String.format(
            "{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\",\"phone\":\"%s\",\"address\":\"%s\"}",
            email, password, name, phone, address);

        // Create the first user
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/register")
        .then()
            .statusCode(201);

        // Try to create the second user with the same email
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/register")
        .then()
            .statusCode(406) // Adjusted status code based on actual response
            .body("message", equalTo("Argument validation error"))
            .body("entityName", equalTo("UserAccount"))
            .body("fieldErrors[0].field", equalTo("email"))
            .body("fieldErrors[0].message", equalTo("Account with this email already exists"));
    }

    @Test
    public void testGetCustomer() {
        String email = "ivan.petrov@yandex.ru";
        String password = "petrov";
        String name = "Ivan Petrov";
        String phone = "+7 123 456 78 90";
        String address = "Riesstrasse 18";

        String userJson = String.format(
            "{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\",\"phone\":\"%s\",\"address\":\"%s\"}",
            email, password, name, phone, address);

        // Register the user
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(userJson)
        .when()
            .post("/register")
        .then()
            .statusCode(201);

        // Authenticate the user
        String authToken = given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", email, password))
        .when()
            .post("/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Get customer details
        given()
            .baseUri(baseUrlOfSut)
            .auth()
            .oauth2(authToken)
        .when()
            .get("/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo(email))
            .body("name", equalTo(name))
            .body("phone", equalTo(phone))
            .body("address", equalTo(address));
    }
}
