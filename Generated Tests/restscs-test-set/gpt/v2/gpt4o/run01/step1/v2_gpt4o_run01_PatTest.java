
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

public class v2_gpt4o_run01_PatTest {

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
    public void testGetPatWithValidInput() {
        String txt = "abcdefg";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("1"));
    }

    @Test
    public void testGetPatWithReversePattern() {
        String txt = "abcdefg";
        String pat = "edc";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("2"));
    }

    @Test
    public void testGetPatWithBothPatternAndReverse() {
        String txt = "abcdefgdecba";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("3"));
    }

    @Test
    public void testGetPatWithPalindromePattern() {
        String txt = "abcdefgfedcba";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("4"));
    }

    @Test
    public void testGetPatWithPalindromeReversePattern() {
        String txt = "gfedcbacdefg";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("5"));
    }

    @Test
    public void testGetPatWithInvalidPattern() {
        String txt = "abcdefg";
        String pat = "xyz";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("0"));
    }

    @Test
    public void testGetPatWithShortPattern() {
        String txt = "abcdefg";
        String pat = "ab";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("0"));
    }

    @Test
    public void testGetPatWithNullPattern() {
        String txt = "abcdefg";
        String pat = null;
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGetPatWithEmptyPattern() {
        String txt = "abcdefg";
        String pat = "";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGetPatWithInvalidTxt() {
        String txt = null;
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGetPatWithEmptyTxt() {
        String txt = "";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("0"));
    }

    @Test
    public void testSchemaValidation() {
        String txt = "abcdefg";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(matchesJsonSchemaInClasspath("pat-schema.json"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Test creating a pattern
        String txt = "abcdefg";
        String pat = "cde";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .post(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(405); // Method not allowed as the endpoint only supports GET
    }
}
