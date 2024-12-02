
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
import java.util.regex.*;

public class v1_gpt35_run03_RegexTest {

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
    public void testRegexEndpoint() {
        String txt = "http://example.com/test";
        given()
            .pathParam("txt", txt)
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCalcEndpoint() {
        given()
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
            .pathParam("op", "add")
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("15.0"));
    }

    @Test
    public void testCookieEndpoint() {
        given()
            .pathParam("name", "cookieName")
            .pathParam("val", "cookieValue")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testCostFunsEndpoint() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "string")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testDateParseEndpoint() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .pathParam("directory", "example")
            .pathParam("file", "test.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testNoTypeVarEndpoint() {
        given()
            .pathParam("i", 10)
            .pathParam("s", "string")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testPatEndpoint() {
        String txt = "123.45e+10";
        given()
            .pathParam("txt", txt)
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("fpe"));
    }

    @Test
    public void testText2TxtEndpoint() {
        given()
            .pathParam("word1", "Hello")
            .pathParam("word2", "World")
            .pathParam("word3", "!")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Hello World !"));
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr male"));
    }

}
