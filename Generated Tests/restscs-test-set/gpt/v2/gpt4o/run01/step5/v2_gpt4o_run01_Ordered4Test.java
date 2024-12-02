
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

public class v2_gpt4o_run01_Ordered4Test {

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
    public void testOrdered4Increasing() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/ordered4/abcde/abcdef/abcdefg/abcdefgh")
                .then()
                .statusCode(200)
                .body(equalTo("ordered"));

        response.assertThat().body(equalTo("ordered"));
    }

    @Test
    public void testOrdered4Decreasing() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/ordered4/abcdefgh/abcdefg/abcdef/abcde")
                .then()
                .statusCode(200)
                .body(equalTo("ordered"));

        response.assertThat().body(equalTo("ordered"));
    }

    @Test
    public void testOrdered4Unordered() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/ordered4/abcde/abcdef/abcd/abcdefgh")
                .then()
                .statusCode(200)
                .body(equalTo("unordered"));

        response.assertThat().body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidLength() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/ordered4/abcd/abcdefg/abcdefgh/abcdef")
                .then()
                .statusCode(200)
                .body(equalTo("unordered"));

        response.assertThat().body(equalTo("unordered"));
    }

    @Test
    public void testCalcEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/calc/add/1.0/2.0")
                .then()
                .statusCode(200)
                .body(matchesPattern("-?\\d+(\\.\\d+)?"));

        double result = Double.parseDouble(response.extract().body().asString());
        assertEquals(3.0, result, 0.0);
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/cookie/testCookie/testValue/testSite")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testCostfunsEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/costfuns/1/test")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testDateParseEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/dateparse/Monday/January")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/filesuffix/testDir/testFile.txt")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testNotypevarEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/notypevar/1/test")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testPatEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/pat/testPattern/testString")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testText2txtEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/text2txt/word1/word2/word3")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testTitleEndpoint() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/title/mr/title")
                .then()
                .statusCode(200)
                .body(matchesPattern(".*"));

        response.assertThat().body(matchesPattern(".*"));
    }

    @Test
    public void testServerErrorSimulation() {
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/ordered4/abcde/abcdef/abcdefg/abcdefgh")
                .then()
                .statusCode(500);

        response.assertThat().statusCode(500);
    }
}
