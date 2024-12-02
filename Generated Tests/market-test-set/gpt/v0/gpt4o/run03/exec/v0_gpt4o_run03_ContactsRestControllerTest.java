
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

public class v0_gpt4o_run03_ContactsRestControllerTest {
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
    public void testGetContacts() {
        // Register a user
        ValidatableResponse response = given().contentType("application/json")
            .body("{\"email\":\"ivan.petrov.test@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then().statusCode(201);
        
        // Log in as the user and get the token
        String token = given().contentType("application/json")
            .body("{\"email\":\"ivan.petrov.test@yandex.ru\",\"password\":\"petrov\"}")
            .post(baseUrlOfSut + "/login")
            .then().statusCode(200)
            .extract().path("token");

        // Get contacts
        response = given().auth().oauth2(token)
            .get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);
        
        // Check response
        response.body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    @Timeout(60)
    public void testUpdateContacts() {
        // Register a user
        ValidatableResponse response = given().contentType("application/json")
            .body("{\"email\":\"ivan.petrov.test@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then().statusCode(201);

        // Log in as the user and get the token
        String token = given().contentType("application/json")
            .body("{\"email\":\"ivan.petrov.test@yandex.ru\",\"password\":\"petrov\"}")
            .post(baseUrlOfSut + "/login")
            .then().statusCode(200)
            .extract().path("token");

        // Update contacts
        response = given().auth().oauth2(token)
            .contentType("application/json")
            .body("{\"address\":\"Neue Strasse 20\",\"phone\":\"+7 987 654 32 10\"}")
            .put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);

        // Check updated response
        response.body("address", equalTo("Neue Strasse 20"))
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    @Timeout(60)
    public void testUnauthorizedAccess() {
        // Attempt to get contacts without authentication
        given().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);

        // Attempt to update contacts without authentication
        given().contentType("application/json")
            .body("{\"address\":\"Neue Strasse 20\",\"phone\":\"+7 987 654 32 10\"}")
            .put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(401);
    }
}
