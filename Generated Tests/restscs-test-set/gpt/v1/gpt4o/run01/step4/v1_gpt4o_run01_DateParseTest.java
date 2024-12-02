
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

public class v1_gpt4o_run01_DateParseTest {

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
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("3"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("2"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "multiply")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("6"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "divide")
            .pathParam("arg1", 6)
            .pathParam("arg2", 2)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("3"));
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionid")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(is("Cookie set"));

        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionid")
            .pathParam("val", "abcde")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(is("Cookie set"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 10)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is(notNullValue()));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(is("2"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "fri")
            .pathParam("monthname", "dec")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(is("13"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "report.pdf")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is("pdf"));

        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "images")
            .pathParam("file", "photo.jpg")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is("jpg"));
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
            .body(is(notNullValue()));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(is(notNullValue()));
    }

    @Test
    public void testRegexEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(is(notNullValue()));
    }

    @Test
    public void testPatEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "he.*o")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("true"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "ho.*o")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("false"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "this")
            .pathParam("word2", "is")
            .pathParam("word3", "test")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(is("this is test"));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "Mr")
            .pathParam("title", "Smith")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(is("Mr Smith"));

        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "Dr")
            .pathParam("title", "Jones")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(is("Dr Jones"));
    }
}
