
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

public class v0_gpt4o_run02_RegexTest {

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
    public void testCalcUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "subtract")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "multiply")
            .pathParam("arg1", 2.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("6.0"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "divide")
            .pathParam("arg1", 6.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "divide")
            .pathParam("arg1", 6.0)
            .pathParam("arg2", 0.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testCookieUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "123456")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "")
            .pathParam("val", "123456")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testCostfunsUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "string")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("Cost function result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", -1)
            .pathParam("s", "string")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testDateParseUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("Date parsed"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testFileSuffixUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "file.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("Suffix detected"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("directory", "")
            .pathParam("file", "file.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testNotypevarUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "var")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("Notypevar result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", -1)
            .pathParam("s", "var")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testOrdered4UsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "word1")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("Ordered4 result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testRegexUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "http://example.com")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("url"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "mon01jan")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("date"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "12.34e+56")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("fpe"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "random text")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testPatUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "he.*o")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Matched"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "hi.*o")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Not Matched"));
    }

    @Test
    public void testText2txtUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Text2txt result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testTitleUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "female")
            .pathParam("title", "ms")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title result"));

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "unknown")
            .pathParam("title", "dr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(400);
    }
}
