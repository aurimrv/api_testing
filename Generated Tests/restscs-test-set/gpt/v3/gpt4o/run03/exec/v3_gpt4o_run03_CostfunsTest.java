
package org.restscs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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

public class v3_gpt4o_run03_CostfunsTest {
    private static final SutHandler controller = new em.embedded.org.restscs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    @BeforeClass
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

    @AfterClass
    public static void tearDown() {
        controller.stopSut();
    }

    @Before
    public void initTest() {
        controller.resetStateOfSUT();
    }

    @Test
    public void testCostfuns() {
        // Test valid case with expected result
        given()
            .pathParam("i", 5)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10"));

        // Test edge case with i < -444
        given()
            .pathParam("i", -445)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));

        // Test edge case with i <= -333
        given()
            .pathParam("i", -333)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("3"));

        // Test edge case with i > 666
        given()
            .pathParam("i", 667)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("4"));

        // Test edge case with i >= 555
        given()
            .pathParam("i", 555)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("5"));

        // Test edge case with i != -4
        given()
            .pathParam("i", -3)
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("6"));

        // Test edge case with s equals "baab"
        given()
            .pathParam("i", 0)
            .pathParam("s", "baab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("7"));

        // Test edge case with s greater than "ababbaba"
        given()
            .pathParam("i", 0)
            .pathParam("s", "abababab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("8"));

        // Test edge case with s greater than or equal to "ababbaba"
        given()
            .pathParam("i", 0)
            .pathParam("s", "ababbaba")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("9"));

        // Test edge case with s not equal to "abab"
        given()
            .pathParam("i", 0)
            .pathParam("s", "abcd")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testInvalidInputs() {
        // Test invalid number input
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "anything")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(400); // Bad Request expected

        // Test invalid string input
        given()
            .pathParam("i", 0)
            .pathParam("s", "invalid")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(400); // Bad Request expected
    }

    @Test
    public void testInternalServerError() {
        // Simulate an internal server error by sending an unexpected input
        given()
            .pathParam("i", Integer.MAX_VALUE)
            .pathParam("s", "unexpected")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(500); // Internal Server Error expected
    }
}
