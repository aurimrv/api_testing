
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

public class run01_ContactsRestControllerTest {
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
    public void testGetContactsUnauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetContactsForbidden() {
        String token = obtainToken("unauthorizedUser", "password");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(403);
    }

    @Test
    public void testGetContactsSuccess() {
        String token = obtainToken("ivan.petrov@yandex.ru", "petrov");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("Riesstrasse 18"))
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        ContactsDTOReq contactsDTOReq = new ContactsDTOReq("New Address", "+7 987 654 32 10");

        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(contactsDTOReq)
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContactsForbidden() {
        String token = obtainToken("unauthorizedUser", "password");

        ContactsDTOReq contactsDTOReq = new ContactsDTOReq("New Address", "+7 987 654 32 10");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
            .contentType("application/json")
            .body(contactsDTOReq)
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(403);
    }

    @Test
    public void testUpdateContactsSuccess() {
        String token = obtainToken("ivan.petrov@yandex.ru", "petrov");

        ContactsDTOReq contactsDTOReq = new ContactsDTOReq("New Address", "+7 987 654 32 10");

        given()
            .baseUri(baseUrlOfSut)
            .auth().oauth2(token)
            .contentType("application/json")
            .body(contactsDTOReq)
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(200)
            .body("address", equalTo("New Address"))
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    private String obtainToken(String username, String password) {
        // Implement this method to obtain a token using username and password
        // This is a dummy implementation to allow tests to run
        return given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(Map.of("username", username, "password", password))
            .when()
            .post("/auth/login")
            .then()
            .statusCode(200)
            .extract()
            .path("token");
    }

    public static class ContactsDTOReq {
        private String address;
        private String phone;

        public ContactsDTOReq(String address, String phone) {
            this.address = address;
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
