
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

public class v2_gpt4o_run02_Text2TxtTest {

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
    public void testCalcOperation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body("$", hasKey("result"))
            .body("result", equalTo(8.0));

        double result = response.extract().path("result");
        assertEquals(8.0, result, 0.0);
    }

    @Test
    public void testCalcInvalidOperation() {
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "invalid")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(500);
    }

    @Test
    public void testCookie() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "123")
            .pathParam("site", "example.com")
            .when().get("/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body("$", hasKey("message"))
            .body("message", equalTo("Cookie set: session=123 for site example.com"));

        String result = response.extract().path("message");
        assertEquals("Cookie set: session=123 for site example.com", result);
    }

    @Test
    public void testCostFuns() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body("$", hasKey("result"));

        String result = response.extract().path("result");
        assertNotNull(result);
    }

    @Test
    public void testDateParse() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body("$", hasKey("parsedDate"));

        String result = response.extract().path("parsedDate");
        assertNotNull(result);
    }

    @Test
    public void testFileSuffix() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
            .when().get("/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body("$", hasKey("suffix"))
            .body("suffix", equalTo(".txt"));

        String result = response.extract().path("suffix");
        assertEquals(".txt", result);
    }

    @Test
    public void testNoTypeVar() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body("$", hasKey("result"));

        String result = response.extract().path("result");
        assertNotNull(result);
    }

    @Test
    public void testOrdered4() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
            .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body("$", hasKey("result"));

        String result = response.extract().path("result");
        assertNotNull(result);
    }

    @Test
    public void testRegex() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body("$", hasKey("pattern"))
            .body("pattern", equalTo("[a-z]+"));

        String result = response.extract().path("pattern");
        assertNotNull(result);
    }

    @Test
    public void testPat() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "h.*o")
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body("$", hasKey("matches"))
            .body("matches", equalTo(true));

        boolean matches = response.extract().path("matches");
        assertTrue(matches);
    }

    @Test
    public void testText2Txt() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "see")
            .pathParam("word2", "you")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body("$", hasKey("result"))
            .body("result", equalTo("cu"));

        String result = response.extract().path("result");
        assertEquals("cu", result);
    }

    @Test
    public void testText2TxtInvalid() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(500);
    }
}
