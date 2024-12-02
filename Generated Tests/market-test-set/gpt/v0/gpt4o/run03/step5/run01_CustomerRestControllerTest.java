
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

// Assuming UserDTO is part of the project's package
import market.dto.UserDTO;

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
        UserDTO user = new UserDTO();
        user.setEmail("ivan.petrov@yandex.ru");
        user.setPassword("petrov");
        user.setName("Ivan Petrov");
        user.setPhone("+7 123 456 78 90");
        user.setAddress("Riesstrasse 18");

        ValidatableResponse response = given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));

        // Check for duplicate email
        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(400);
    }

    @Test
    public void testGetCustomer() {
        // First, register the user
        UserDTO user = new UserDTO();
        user.setEmail("ivan.petrov@yandex.ru");
        user.setPassword("petrov");
        user.setName("Ivan Petrov");
        user.setPhone("+7 123 456 78 90");
        user.setAddress("Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Then, authenticate the user to get the token
        String token = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
        .when()
            .post(baseUrlOfSut + "/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Now, get the customer details
        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));

        // Test unauthorized access
        given()
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }
    
    // Additional tests to cover specification and all possible responses (status code)

    @Test
    public void testUnauthorizedAccessToGetCustomer() {
        given()
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testForbiddenAccessToGetCustomer() {
        // Assume we have another user with a different role that should not access the customer endpoint
        String adminEmail = "admin@domain.com";
        String adminPassword = "admin";

        // Register admin user
        UserDTO adminUser = new UserDTO();
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(adminPassword);
        adminUser.setName("Admin User");
        adminUser.setPhone("+1 123 456 78 90");
        adminUser.setAddress("Admin Street");

        given()
            .contentType("application/json")
            .body(adminUser)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        // Authenticate admin user to get the token
        String token = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
        .when()
            .post(baseUrlOfSut + "/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Try to access the customer endpoint with admin token
        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(403);
    }
}
