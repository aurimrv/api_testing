
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

public class v3_gpt4o_run01_PatTest {

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
        given().pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("3.0"));
        
        given().pathParam("op", "div")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 0.0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(500);
    }

    @Test
    public void testCookieUsingGET() {
        given().pathParam("name", "test")
            .pathParam("val", "value")
            .pathParam("site", "example.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("test=value; Domain=example.com"));
    }
    
    @Test
    public void testCostfunsUsingGET() {
        given().pathParam("i", 1)
            .pathParam("s", "test")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("1 test"));
    }
    
    @Test
    public void testDateParseUsingGET() {
        given().pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200);
    }
    
    @Test
    public void testFileSuffixUsingGET() {
        given().pathParam("directory", "dir")
            .pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("txt"));
    }
    
    @Test
    public void testNotyPevarUsingGET() {
        given().pathParam("i", 1)
            .pathParam("s", "test")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("1 test"));
    }
    
    @Test
    public void testOrdered4UsingGET() {
        given().pathParam("w", "w")
            .pathParam("x", "x")
            .pathParam("y", "y")
            .pathParam("z", "z")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200).body(equalTo("wxyz"));
    }

    @Test
    public void testPatUsingGET() {
        given().pathParam("txt", "text")
            .pathParam("pat", "ex")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("1"));

        given().pathParam("txt", "text")
            .pathParam("pat", "txe")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("2"));

        given().pathParam("txt", "text")
            .pathParam("pat", "ext")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("0"));
    }

    @Test
    public void testText2txtUsingGET() {
        given().pathParam("word1", "a")
            .pathParam("word2", "b")
            .pathParam("word3", "c")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("abc"));
    }

    @Test
    public void testTitleUsingGET() {
        given().pathParam("sex", "Mr")
            .pathParam("title", "Smith")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("Mr Smith"));
    }
}
