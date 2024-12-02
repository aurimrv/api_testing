
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

public class v1_gpt4o_run03_CostfunsTest {
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
        given().pathParam("op", "add")
                .pathParam("arg1", 1.0)
                .pathParam("arg2", 2.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(200)
                .body(equalTo("3.0"));

        given().pathParam("op", "subtract")
                .pathParam("arg1", 5.0)
                .pathParam("arg2", 3.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(200)
                .body(equalTo("2.0"));

        given().pathParam("op", "divide")
                .pathParam("arg1", 4.0)
                .pathParam("arg2", 2.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(200)
                .body(equalTo("2.0"));

        given().pathParam("op", "multiply")
                .pathParam("arg1", 2.0)
                .pathParam("arg2", 3.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(200)
                .body(equalTo("6.0"));

        given().pathParam("op", "unknown")
                .pathParam("arg1", 2.0)
                .pathParam("arg2", 3.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given().pathParam("name", "session")
                .pathParam("val", "12345")
                .pathParam("site", "example.com")
                .when()
                .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
                .then()
                .statusCode(200)
                .body(containsString("Cookie set"));

        given().pathParam("name", "")
                .pathParam("val", "value")
                .pathParam("site", "example.com")
                .when()
                .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        given().pathParam("i", 5)
                .pathParam("s", "abc")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("1"));

        given().pathParam("i", -445)
                .pathParam("s", "xyz")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("2"));

        given().pathParam("i", -333)
                .pathParam("s", "xyz")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("3"));

        given().pathParam("i", 667)
                .pathParam("s", "xyz")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("4"));

        given().pathParam("i", 555)
                .pathParam("s", "xyz")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("5"));

        given().pathParam("i", 1)
                .pathParam("s", "ab")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("6"));

        given().pathParam("i", 1)
                .pathParam("s", "baab")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("7"));

        given().pathParam("i", 1)
                .pathParam("s", "ababab")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("8"));

        given().pathParam("i", 1)
                .pathParam("s", "ababab")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("9"));

        given().pathParam("i", 1)
                .pathParam("s", "abab")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(equalTo("10"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().pathParam("dayname", "Monday")
                .pathParam("monthname", "January")
                .when()
                .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("dayname", "Funday")
                .pathParam("monthname", "Januember")
                .when()
                .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().pathParam("directory", "docs")
                .pathParam("file", "report.txt")
                .when()
                .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then()
                .statusCode(200)
                .body(containsString(".txt"));

        given().pathParam("directory", "docs")
                .pathParam("file", "report")
                .when()
                .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testNotypevarEndpoint() {
        given().pathParam("i", 123)
                .pathParam("s", "test")
                .when()
                .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("i", 0)
                .pathParam("s", "")
                .when()
                .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testOrdered4Endpoint() {
        given().pathParam("w", "one")
                .pathParam("x", "two")
                .pathParam("y", "three")
                .pathParam("z", "four")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("w", "one")
                .pathParam("x", "two")
                .pathParam("y", "")
                .pathParam("z", "four")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testRegexEndpoint() {
        given().pathParam("txt", "example")
                .when()
                .get(baseUrlOfSut + "/api/pat/{txt}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("txt", "")
                .when()
                .get(baseUrlOfSut + "/api/pat/{txt}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testPatEndpoint() {
        given().pathParam("txt", "example")
                .pathParam("pat", "ex.*")
                .when()
                .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("txt", "example")
                .pathParam("pat", "")
                .when()
                .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testText2txtEndpoint() {
        given().pathParam("word1", "Hello")
                .pathParam("word2", "World")
                .pathParam("word3", "!")
                .when()
                .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("word1", "Hello")
                .pathParam("word2", "")
                .pathParam("word3", "!")
                .when()
                .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        given().pathParam("sex", "male")
                .pathParam("title", "Mr")
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .body(notNullValue());

        given().pathParam("sex", "unknown")
                .pathParam("title", "Mx")
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(404);
    }
}
