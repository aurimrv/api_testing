
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

public class v3_gpt4o_run01_RegexTest {
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
    public void testGetCalc() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/add/2/3")
                .then().statusCode(200)
                .body(equalTo("5"));
    }

    @Test
    public void testGetCalcInvalidOp() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/invalid/2/3")
                .then().statusCode(404);
    }

    @Test
    public void testGetCalcInternalServerError() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/divide/2/0")
                .then().statusCode(500);
    }

    @Test
    public void testGetCookie() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/testname/testval/testsite")
                .then().statusCode(200)
                .body(equalTo("Cookie set"));
    }

    @Test
    public void testGetCostfuns() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/costfuns/1/teststring")
                .then().statusCode(200)
                .body(equalTo("Cost function result"));
    }

    @Test
    public void testGetDateParse() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/mon/jan")
                .then().statusCode(200)
                .body(equalTo("Date parsed"));
    }

    @Test
    public void testGetFileSuffix() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/dir/file.txt")
                .then().statusCode(200)
                .body(equalTo("txt"));
    }

    @Test
    public void testGetNotypevar() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/1/test")
                .then().statusCode(200)
                .body(equalTo("Variable type not found"));
    }

    @Test
    public void testGetOrdered4() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/a/b/c/d")
                .then().statusCode(200)
                .body(equalTo("Ordered"));
    }

    @Test
    public void testGetRegex() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/mon01jan")
                .then().statusCode(200)
                .body(equalTo("date"));
    }

    @Test
    public void testGetRegexInvalid() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/invalid")
                .then().statusCode(200)
                .body(equalTo("none"));
    }

    @Test
    public void testGetPat() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/mon01jan/date")
                .then().statusCode(200)
                .body(equalTo("date"));
    }

    @Test
    public void testGetText2txt() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/text2txt/word1/word2/word3")
                .then().statusCode(200)
                .body(equalTo("Text converted"));
    }

    @Test
    public void testGetTitle() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/title/mr/testtitle")
                .then().statusCode(200)
                .body(equalTo("Title matched"));
    }
}
