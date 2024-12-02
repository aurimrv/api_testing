
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

public class v1_gpt4o_run02_PatTest {

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
        // Example test for /api/calc/{op}/{arg1}/{arg2}
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
    }

    @Test
    public void testCookieEndpoint() {
        // Example test for /api/cookie/{name}/{val}/{site}
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testCostfunsEndpoint() {
        // Example test for /api/costfuns/{i}/{s}
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "example")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testDateParseEndpoint() {
        // Example test for /api/dateparse/{dayname}/{monthname}
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testFileSuffixEndpoint() {
        // Example test for /api/filesuffix/{directory}/{file}
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "/home/user")
            .pathParam("file", "document.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testNotyPevarEndpoint() {
        // Example test for /api/notypevar/{i}/{s}
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testOrdered4Endpoint() {
        // Example test for /api/ordered4/{w}/{x}/{z}/{y}
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("z", "c")
            .pathParam("y", "d")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testPatEndpoint() {
        // Example test for /api/pat/{txt}
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(notNullValue());

        // Example test for /api/pat/{txt}/{pat}
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
            .pathParam("pat", "amp")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testText2txtEndpoint() {
        // Example test for /api/text2txt/{word1}/{word2}/{word3}
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "test")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testTitleEndpoint() {
        // Example test for /api/title/{sex}/{title}
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }
}
