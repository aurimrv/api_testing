
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
import io.restassured.path.json.JsonPath;
import java.util.Arrays;
import io.restassured.module.jsv.JsonSchemaValidator;

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
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(user.getEmail()))
            .body("name", equalTo(user.getName()))
            .body("phone", equalTo(user.getPhone()))
            .body("address", equalTo(user.getAddress()));
    }

    @Test
    public void testCreateCustomerWithExistingEmail() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(400)
            .body("message", containsString("EmailExistsException"));
    }

    @Test
    public void testGetCustomer() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);

        given()
            .auth().preemptive().basic(user.getEmail(), user.getPassword())
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo(user.getEmail()))
            .body("name", equalTo(user.getName()))
            .body("phone", equalTo(user.getPhone()))
            .body("address", equalTo(user.getAddress()));
    }

    @Test
    public void testCreateCustomerWithInvalidData() {
        UserDTO user = new UserDTO("", "", "Ivan Petrov", "123", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(400)
            .body("message", containsString("Validation failed"));
    }

    @Test
    public void testInternalServerError() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        controller.stopSut();

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(500);

        controller.startSut();
    }

    @Test
    public void testSchemaValidation() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserDTORes.json"));
    }
}

class UserDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    public UserDTO(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
