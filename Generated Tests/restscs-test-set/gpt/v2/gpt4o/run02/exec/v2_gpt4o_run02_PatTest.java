
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

public class v2_gpt4o_run02_PatTest {

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
    public void testPatEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("txt", "abcde")
            .pathParam("pat", "abc")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("1"));
    }

    @Test
    public void testPatEndpoint_ReversePattern() {
        ValidatableResponse response = given()
            .pathParam("txt", "edcba")
            .pathParam("pat", "abc")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("2"));
    }

    @Test
    public void testPatEndpoint_PalindromePattern() {
        ValidatableResponse response = given()
            .pathParam("txt", "abccba")
            .pathParam("pat", "abc")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("0"));
    }

    @Test
    public void testPatEndpoint_PatternNotFound() {
        ValidatableResponse response = given()
            .pathParam("txt", "abcdefg")
            .pathParam("pat", "xyz")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("0"));
    }

    @Test
    public void testPatEndpoint_ErrorScenario() {
        ValidatableResponse response = given()
            .pathParam("txt", "")
            .pathParam("pat", "abc")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("none"));
    }

    @Test
    public void testCalcEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("0.0"));
    }

    @Test
    public void testCalcEndpoint_InvalidOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "invalid")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("0.0"));
    }

    @Test
    public void testCostfunsEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(matchesPattern("\\d+"));
    }

    @Test
    public void testDateParseEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(is("0"));
    }

    @Test
    public void testFileSuffixEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("directory", "home")
            .pathParam("file", "example.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is("0"));
    }

    @Test
    public void testNotyPevarEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is("2"));
    }

    @Test
    public void testOrdered4Endpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("z", "c")
            .pathParam("y", "d")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(is("unordered"));
    }

    @Test
    public void testTitleEndpoint_ValidInputs() {
        ValidatableResponse response = given()
            .pathParam("sex", "M")
            .pathParam("title", "Dr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(is("-1"));
    }
}
