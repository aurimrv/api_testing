
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

public class v1_gpt4o_run01_Ordered4Test {
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
        ValidatableResponse response = given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("3.0"));
        
        response = given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCookieUsingGET() {
        ValidatableResponse response = given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "localhost")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testCostfunsUsingGET() {
        ValidatableResponse response = given()
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("Cost calculated"));
    }

    @Test
    public void testDateParseUsingGET() {
        ValidatableResponse response = given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("Date parsed"));
    }

    @Test
    public void testFileSuffixUsingGET() {
        ValidatableResponse response = given()
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo(".txt"));
    }

    @Test
    public void testNotyPevarUsingGET() {
        ValidatableResponse response = given()
            .pathParam("i", 42)
            .pathParam("s", "test")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("No type var"));
    }

    @Test
    public void testOrdered4UsingGET() {
        ValidatableResponse response = given()
            .pathParam("w", "abcde")
            .pathParam("x", "bcdef")
            .pathParam("y", "cdefg")
            .pathParam("z", "defgh")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("increasing"));

        response = given()
            .pathParam("w", "defgh")
            .pathParam("x", "cdefg")
            .pathParam("y", "bcdef")
            .pathParam("z", "abcde")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("decreasing"));

        response = given()
            .pathParam("w", "abcde")
            .pathParam("x", "cdefg")
            .pathParam("y", "bcdef")
            .pathParam("z", "defgh")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testRegexUsingGET() {
        ValidatableResponse response = given()
            .pathParam("txt", "test")
            .when().get(baseUrlOfSut + "/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testPatUsingGET() {
        ValidatableResponse response = given()
            .pathParam("txt", "test")
            .pathParam("pat", "t.st")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testText2txtUsingGET() {
        ValidatableResponse response = given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("Text converted"));
    }

    @Test
    public void testTitleUsingGET() {
        ValidatableResponse response = given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(equalTo("Title matched"));
    }
}
