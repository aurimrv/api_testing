
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
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v3_gpt4o_run02_RegexTest {

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
    public void testCalc() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/add/10/5")
                .then().statusCode(200);
        res.body(equalTo("15.0"));
    }

    @Test
    public void testCalcInvalidOperation() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/invalidOp/10/5")
                .then().statusCode(404);
    }

    @Test
    public void testCalcInvalidPath() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/add/10")
                .then().statusCode(404);
    }

    @Test
    public void testCookie() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/testName/testVal/testSite")
                .then().statusCode(200);
        res.body(equalTo("name=testName; value=testVal; site=testSite"));
    }

    @Test
    public void testCostFuns() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/costfuns/1/testString")
                .then().statusCode(200);
        res.body(equalTo("Cost function result"));
    }

    @Test
    public void testDateParse() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/mon/jan")
                .then().statusCode(200);
        res.body(equalTo("Monday January"));
    }

    @Test
    public void testFileSuffix() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/dir/file.txt")
                .then().statusCode(200);
        res.body(equalTo("txt"));
    }

    @Test
    public void testNoTypeVar() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/1/string")
                .then().statusCode(200);
        res.body(equalTo("Notypevar result"));
    }

    @Test
    public void testOrdered4() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/a/b/c/d")
                .then().statusCode(200);
        res.body(equalTo("Ordered 4 result"));
    }

    @Test
    public void testRegexUrl() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/http://example.com")
                .then().statusCode(200);
        res.body(equalTo("url"));
    }

    @Test
    public void testRegexDate() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/mon01jan")
                .then().statusCode(200);
        res.body(equalTo("date"));
    }

    @Test
    public void testRegexFpe() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/12.34e+56")
                .then().statusCode(200);
        res.body(equalTo("fpe"));
    }

    @Test
    public void testRegexNone() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/abcdef")
                .then().statusCode(200);
        res.body(equalTo("none"));
    }

    @Test
    public void testTitle() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .when().get("/api/title/mr/dr")
                .then().statusCode(200);
        res.body(equalTo("Title result"));
    }

    @Test
    public void testInternalServerErrors() {
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/divide/10/0")
                .then().statusCode(500);

        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/abc/def")
                .then().statusCode(500);

        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/mon/invalidMonth")
                .then().statusCode(500);
    }
}
