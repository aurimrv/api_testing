
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

public class v1_gpt4turbo_run03_CustomerRestControllerTest {

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
    public void testGetCustomerAsAdmin() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().preemptive().basic("admin@example.com", "admin")
            .when()
            .get("/api/customer")
            .then()
            .statusCode(200)
            .body("email", equalTo("admin@example.com"));
    }

    @Test
    public void testGetCustomerAsUser() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().preemptive().basic("user@example.com", "user")
            .when()
            .get("/api/customer")
            .then()
            .statusCode(200)
            .body("email", equalTo("user@example.com"));
    }

    @Test
    public void testGetCustomerUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/customer")
            .then()
            .statusCode(401);
    }

    @Test
    public void testCreateCustomerSuccessful() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"new.user@example.com\",\"password\":\"password\",\"name\":\"New User\",\"phone\":\"+1 234 567 8901\",\"address\":\"123 New St\"}")
            .when()
            .post("/api/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("new.user@example.com"));
    }

    @Test
    public void testCreateCustomerEmailExists() {
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"user@example.com\",\"password\":\"password\",\"name\":\"User\",\"phone\":\"+1 234 567 8901\",\"address\":\"123 User St\"}")
            .when()
            .post("/api/register")
            .then()
            .statusCode(409); // Updated to reflect the actual status code for EmailExistsException
    }
}
