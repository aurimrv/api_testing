
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

public class v1_gpt4o_run02_Ordered4Test {
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
    public void testCalcEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 10)
            .pathParam("arg2", 20)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("30.0"));
        
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "sub")
            .pathParam("arg1", 20)
            .pathParam("arg2", 10)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("10.0"));
        
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "mul")
            .pathParam("arg1", 10)
            .pathParam("arg2", 20)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("200.0"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "div")
            .pathParam("arg1", 20)
            .pathParam("arg2", 10)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
        
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "invalidOp")
            .pathParam("arg1", 20)
            .pathParam("arg2", 10)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionId")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
        
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "")
            .pathParam("val", "")
            .pathParam("site", "")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "InvalidDay")
            .pathParam("monthname", "InvalidMonth")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "")
            .pathParam("file", "")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testOrdered4Endpoint() {
        // Test increasing order
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));

        // Test decreasing order
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "apple")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));

        // Test unordered
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testRegexEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "test")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "test")
            .pathParam("pat", "pattern")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "java")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo(""));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }
}
