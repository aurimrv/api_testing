
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

public class v3_gpt4o_run03_RegexTest {

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
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("op", "add")
                .pathParam("arg1", 1)
                .pathParam("arg2", 2)
                .when().get("/api/calc/{op}/{arg1}/{arg2}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("3"));

        res = given().baseUri(baseUrlOfSut)
                .pathParam("op", "div")
                .pathParam("arg1", 1)
                .pathParam("arg2", 0)
                .when().get("/api/calc/{op}/{arg1}/{arg2}")
                .then().statusCode(500);
    }

    @Test
    public void testCookieUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("name", "session")
                .pathParam("val", "123")
                .pathParam("site", "example.com")
                .when().get("/api/cookie/{name}/{val}/{site}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("name", "")
                .pathParam("val", "")
                .pathParam("site", "")
                .when().get("/api/cookie/{name}/{val}/{site}")
                .then().statusCode(404);
    }

    @Test
    public void testCostfunsUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("i", 1)
                .pathParam("s", "example")
                .when().get("/api/costfuns/{i}/{s}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("i", -1)
                .pathParam("s", "")
                .when().get("/api/costfuns/{i}/{s}")
                .then().statusCode(404);
    }

    @Test
    public void testDateParseUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("dayname", "mon")
                .pathParam("monthname", "jan")
                .when().get("/api/dateparse/{dayname}/{monthname}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("2"));

        res = given().baseUri(baseUrlOfSut)
                .pathParam("dayname", "invalid")
                .pathParam("monthname", "invalid")
                .when().get("/api/dateparse/{dayname}/{monthname}")
                .then().statusCode(500);
    }

    @Test
    public void testFileSuffixUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("directory", "home")
                .pathParam("file", "file.txt")
                .when().get("/api/filesuffix/{directory}/{file}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("directory", "")
                .pathParam("file", "")
                .when().get("/api/filesuffix/{directory}/{file}")
                .then().statusCode(404);
    }

    @Test
    public void testNotypevarUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("i", 1)
                .pathParam("s", "example")
                .when().get("/api/notypevar/{i}/{s}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("i", -1)
                .pathParam("s", "")
                .when().get("/api/notypevar/{i}/{s}")
                .then().statusCode(404);
    }

    @Test
    public void testOrdered4UsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("w", "1")
                .pathParam("x", "2")
                .pathParam("y", "3")
                .pathParam("z", "4")
                .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("w", "")
                .pathParam("x", "")
                .pathParam("y", "")
                .pathParam("z", "")
                .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
                .then().statusCode(404);
    }

    @Test
    public void testRegexUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("txt", "http://example.com")
                .when().get("/api/pat/{txt}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("0"));

        res = given().baseUri(baseUrlOfSut)
                .pathParam("txt", "invalid")
                .when().get("/api/pat/{txt}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("none"));
    }

    @Test
    public void testPatUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("txt", "example")
                .pathParam("pat", "example")
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("1"));

        res = given().baseUri(baseUrlOfSut)
                .pathParam("txt", "invalid")
                .pathParam("pat", "example")
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);
        res.assertThat().body(equalTo("none"));
    }

    @Test
    public void testText2txtUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("word1", "one")
                .pathParam("word2", "two")
                .pathParam("word3", "three")
                .when().get("/api/text2txt/{word1}/{word2}/{word3}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("word1", "")
                .pathParam("word2", "")
                .pathParam("word3", "")
                .when().get("/api/text2txt/{word1}/{word2}/{word3}")
                .then().statusCode(404);
    }

    @Test
    public void testTitleUsingGET() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
                .pathParam("sex", "male")
                .pathParam("title", "mr")
                .when().get("/api/title/{sex}/{title}")
                .then().statusCode(200);
        res.assertThat().body(notNullValue());

        res = given().baseUri(baseUrlOfSut)
                .pathParam("sex", "")
                .pathParam("title", "")
                .when().get("/api/title/{sex}/{title}")
                .then().statusCode(404);
    }
}
