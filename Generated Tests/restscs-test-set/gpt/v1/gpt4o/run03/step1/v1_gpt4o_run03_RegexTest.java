
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

public class v1_gpt4o_run03_RegexTest {

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
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));

        given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));

        given()
            .pathParam("op", "multiply")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("6.0"));

        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 6)
            .pathParam("arg2", 2)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));

        given()
            .pathParam("op", "unknown")
            .pathParam("arg1", 1)
            .pathParam("arg2", 1)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given()
            .pathParam("name", "testName")
            .pathParam("val", "testVal")
            .pathParam("site", "testSite")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("cookie set"));

        given()
            .pathParam("name", "")
            .pathParam("val", "testVal")
            .pathParam("site", "testSite")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200);

        given()
            .pathParam("i", -1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(404);
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
            .body(equalTo("date parsed"));

        given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .pathParam("directory", "dir")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));

        given()
            .pathParam("directory", "dir")
            .pathParam("file", "file")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200);

        given()
            .pathParam("i", -1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testOrdered4Endpoint() {
        given()
            .pathParam("w", "word1")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("ordered"));

        given()
            .pathParam("w", "word1")
            .pathParam("x", "")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testRegexEndpoint() {
        given()
            .pathParam("txt", "http://example.com")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("url"));

        given()
            .pathParam("txt", "mon01jan")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("date"));

        given()
            .pathParam("txt", "invalid")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testPatEndpoint() {
        given()
            .pathParam("txt", "example")
            .pathParam("pat", "ex")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("true"));

        given()
            .pathParam("txt", "example")
            .pathParam("pat", "nope")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("false"));
    }

    @Test
    public void testText2txtEndpoint() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("onetwothree"));

        given()
            .pathParam("word1", "one")
            .pathParam("word2", "")
            .pathParam("word3", "three")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr."));

        given()
            .pathParam("sex", "female")
            .pathParam("title", "dr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Dr."));

        given()
            .pathParam("sex", "unknown")
            .pathParam("title", "mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(404);
    }
}
