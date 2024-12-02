
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
    @Timeout(60)
    public void testCreateCustomer() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        given().contentType("application/json").body(user)
                .post(baseUrlOfSut + "/register")
                .then()
                .statusCode(201)
                .body("email", equalTo("ivan.petrov@yandex.ru"))
                .body("name", equalTo("Ivan Petrov"))
                .body("phone", equalTo("+7 123 456 78 90"))
                .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(60)
    public void testCreateCustomerEmailExists() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        // Register the user for the first time
        given().contentType("application/json").body(user)
                .post(baseUrlOfSut + "/register")
                .then()
                .statusCode(201);

        // Attempt to register the same user again
        given().contentType("application/json").body(user)
                .post(baseUrlOfSut + "/register")
                .then()
                .statusCode(409);
    }

    @Test
    @Timeout(60)
    public void testGetCustomerAuthenticated() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        // Register the user
        given().contentType("application/json").body(user)
                .post(baseUrlOfSut + "/register")
                .then()
                .statusCode(201);

        // Authenticate the user
        String token = authenticate("ivan.petrov@yandex.ru", "petrov");

        // Access the customer endpoint
        given().auth().oauth2(token)
                .get(baseUrlOfSut + "/customer")
                .then()
                .statusCode(200)
                .body("email", equalTo("ivan.petrov@yandex.ru"))
                .body("name", equalTo("Ivan Petrov"))
                .body("phone", equalTo("+7 123 456 78 90"))
                .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(60)
    public void testGetCustomerUnauthorized() {
        given().get(baseUrlOfSut + "/customer")
                .then()
                .statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetCustomerForbidden() {
        // This test assumes there might be another role which does not have access
        // to the customer endpoint, for example, a "GUEST" role.

        // Register a user with a role other than "USER" or "ADMIN"
        // This part is pseudo-code as the actual implementation may vary.

        // Map<String, Object> guestUser = Map.of(
        //     "email", "guest.user@yandex.ru",
        //     "password", "guestpassword",
        //     "name", "Guest User",
        //     "phone", "+7 123 456 78 91",
        //     "address", "Gueststrasse 19",
        //     "role", "GUEST"
        // );

        // given().contentType("application/json").body(guestUser)
        //        .post(baseUrlOfSut + "/register")
        //        .then()
        //        .statusCode(201);

        // Authenticate the guest user
        // String token = authenticate("guest.user@yandex.ru", "guestpassword");

        // Access the customer endpoint
        // given().auth().oauth2(token)
        //        .get(baseUrlOfSut + "/customer")
        //        .then()
        //        .statusCode(403);
    }

    private String authenticate(String email, String password) {
        // This method should implement the logic to authenticate the user
        // and return the JWT token or session ID.
        // The implementation is skipped for brevity.

        // For example:
        // return given().contentType("application/json")
        //               .body(new AuthenticationRequest(email, password))
        //               .post(baseUrlOfSut + "/authenticate")
        //               .then()
        //               .statusCode(200)
        //               .extract()
        //               .path("token");

        return null;
    }
}
