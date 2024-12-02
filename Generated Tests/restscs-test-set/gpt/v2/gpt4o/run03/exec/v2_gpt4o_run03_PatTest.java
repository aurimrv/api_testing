
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

public class v2_gpt4o_run03_PatTest {

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
        // Valid inputs
        given().pathParam("op", "add").pathParam("arg1", 5).pathParam("arg2", 3)
                .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then().statusCode(200).body(is("8.0"));  // Corrected from equalTo to is
                
        // Invalid operation
        given().pathParam("op", "invalid_op").pathParam("arg1", 5).pathParam("arg2", 3)
                .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then().statusCode(400);
    }

    @Test
    public void testCookieEndpoint() {
        // Valid inputs
        given().pathParam("name", "user").pathParam("val", "admin").pathParam("site", "example.com")
                .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
                .then().statusCode(200).body(is("Cookie set"));  // Corrected from equalTo to is
                
        // Missing required parameters
        given().pathParam("name", "user").pathParam("val", "admin")
                .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/")
                .then().statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        // Valid inputs
        given().pathParam("i", 1).pathParam("s", "test")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(is("Cost calculated"));  // Corrected from equalTo to is
                
        // Invalid integer
        given().pathParam("i", "invalid_int").pathParam("s", "test")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(400);
    }

    @Test
    public void testDateParseEndpoint() {
        // Valid inputs
        given().pathParam("dayname", "Monday").pathParam("monthname", "January")
                .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                .then().statusCode(200).body(is("Date parsed"));  // Corrected from equalTo to is
                
        // Invalid day name
        given().pathParam("dayname", "Notaday").pathParam("monthname", "January")
                .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                .then().statusCode(400);
    }

    @Test
    public void testFileSuffixEndpoint() {
        // Valid inputs
        given().pathParam("directory", "dir").pathParam("file", "file.txt")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200).body(is("Suffix found"));  // Corrected from equalTo to is
                
        // Missing required parameters
        given().pathParam("directory", "dir")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/")
                .then().statusCode(404);
    }

    @Test
    public void testNotypevarEndpoint() {
        // Valid inputs
        given().pathParam("i", 1).pathParam("s", "test")
                .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then().statusCode(200).body(is("Type var processed"));  // Corrected from equalTo to is
                
        // Invalid integer
        given().pathParam("i", "invalid_int").pathParam("s", "test")
                .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then().statusCode(400);
    }

    @Test
    public void testOrdered4Endpoint() {
        // Valid inputs
        given().pathParam("w", "a").pathParam("x", "b").pathParam("y", "c").pathParam("z", "d")
                .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then().statusCode(200).body(is("Ordered"));  // Corrected from equalTo to is
                
        // Missing required parameters
        given().pathParam("w", "a").pathParam("x", "b").pathParam("y", "c")
                .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then().statusCode(404);
    }

    @Test
    public void testPatEndpoint() {
        // Valid inputs
        given().pathParam("txt", "abcdef").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(is("Pattern found"));  // Corrected from equalTo to is
                
        // Pattern too short
        given().pathParam("txt", "abcdef").pathParam("pat", "a")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(400);
    }

    @Test
    public void testText2txtEndpoint() {
        // Valid inputs
        given().pathParam("word1", "hello").pathParam("word2", "world").pathParam("word3", "!")
                .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
                .then().statusCode(200).body(is("Text processed"));  // Corrected from equalTo to is
                
        // Missing required parameters
        given().pathParam("word1", "hello").pathParam("word2", "world")
                .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/")
                .then().statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        // Valid inputs
        given().pathParam("sex", "male").pathParam("title", "Mr")
                .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then().statusCode(200).body(is("Title processed"));  // Corrected from equalTo to is
                
        // Missing required parameters
        given().pathParam("sex", "male")
                .when().get(baseUrlOfSut + "/api/title/{sex}/")
                .then().statusCode(404);
    }
}
