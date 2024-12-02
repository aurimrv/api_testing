
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

public class v1_gpt4o_run01_PatTest {

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
                .pathParam("arg1", 1.0)
                .pathParam("arg2", 2.0)
            .when()
                .get("/api/calc/{op}/{arg1}/{arg2}")
            .then()
                .statusCode(200)
                .body(equalTo("3.0"));
                
        given().baseUri(baseUrlOfSut)
                .pathParam("op", "subtract")
                .pathParam("arg1", 5.0)
                .pathParam("arg2", 3.0)
            .when()
                .get("/api/calc/{op}/{arg1}/{arg2}")
            .then()
                .statusCode(200)
                .body(equalTo("2.0"));
                
        given().baseUri(baseUrlOfSut)
                .pathParam("op", "multiply")
                .pathParam("arg1", 2.0)
                .pathParam("arg2", 3.0)
            .when()
                .get("/api/calc/{op}/{arg1}/{arg2}")
            .then()
                .statusCode(200)
                .body(equalTo("6.0"));
                
        given().baseUri(baseUrlOfSut)
                .pathParam("op", "divide")
                .pathParam("arg1", 6.0)
                .pathParam("arg2", 3.0)
            .when()
                .get("/api/calc/{op}/{arg1}/{arg2}")
            .then()
                .statusCode(200)
                .body(equalTo("2.0"));
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("name", "session")
                .pathParam("val", "abc123")
                .pathParam("site", "example.com")
            .when()
                .get("/api/cookie/{name}/{val}/{site}")
            .then()
                .statusCode(200)
                .body(equalTo("cookie set"));
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
                .body(equalTo("cost function result"));
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
                .body(equalTo("parsed date"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("directory", "home")
                .pathParam("file", "document.txt")
            .when()
                .get("/api/filesuffix/{directory}/{file}")
            .then()
                .statusCode(200)
                .body(equalTo(".txt"));
    }

    @Test
    public void testNotypevarEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", 5)
                .pathParam("s", "variable")
            .when()
                .get("/api/notypevar/{i}/{s}")
            .then()
                .statusCode(200)
                .body(equalTo("notypevar result"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("w", "word1")
                .pathParam("x", "word2")
                .pathParam("z", "word3")
                .pathParam("y", "word4")
            .when()
                .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
                .statusCode(200)
                .body(equalTo("ordered words"));
    }

    @Test
    public void testRegexEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("txt", "some%20text")
            .when()
                .get("/api/pat/{txt}")
            .then()
                .statusCode(200)
                .body(equalTo("regex result"));
    }

    @Test
    public void testPatEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("txt", "some%20text")
                .pathParam("pat", "pattern")
            .when()
                .get("/api/pat/{txt}/{pat}")
            .then()
                .statusCode(200)
                .body(equalTo("pat result"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
                .pathParam("word1", "hello")
                .pathParam("word2", "world")
                .pathParam("word3", "test")
            .when()
                .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
                .statusCode(200)
                .body(equalTo("concatenated text"));
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
                .body(equalTo("title result"));
    }
}
