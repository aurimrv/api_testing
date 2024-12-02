
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
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run08_CustomerRestControllerTest {
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
    void testGetCustomer_200() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"));
    }

    @Test
    void testGetCustomer_401() {
        given()
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(401);
    }

    @Test
    void testGetCustomer_403() {
        given().auth().preemptive().basic("unauthorized@mail.com", "wrongpassword")
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(403);
    }

    @Test
    void testCreateCustomer_201() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");
        given().contentType("application/json")
            .body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    void testCreateCustomer_400() {
        UserDTO user = new UserDTO("invalidemail", "123", "", "", "");
        given().contentType("application/json")
            .body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(400);
    }

    @Test
    void testCreateCustomer_409() {
        UserDTO user = new UserDTO("ivan.petrov@yandex.ru", "petrov", "Ivan Petrov", "+7 123 456 78 90", "Riesstrasse 18");
        given().contentType("application/json")
            .body(user)
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(409);
    }

    static class UserDTO {
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

        // Getters and Setters
    }
}
