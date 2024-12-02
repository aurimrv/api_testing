
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

public class v3_gpt4o_run03_Ordered4Test {
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
    public void testCalcUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testCalcUsingGET_404() {
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "unknown")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionId")
            .pathParam("val", "123456")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testCostfunsUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testDateParseUsingGET_200() {
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
    public void testFileSuffixUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "manual.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testOrdered4UsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4UsingGET_200_2() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "apple")
        .when()
            .get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4UsingGET_Unordered() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "date")
            .pathParam("z", "cherry")
        .when()
            .get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testPatUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "he.*")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("true"));
    }

    @Test
    public void testText2txtUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "!")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("hello world !"));
    }

    @Test
    public void testTitleUsingGET_200() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr."));
    }

    @Test
    public void testInternalServerError() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "12345")
            .pathParam("x", "12345")
            .pathParam("y", "12345")
            .pathParam("z", "12345")
        .when()
            .get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(500);
    }
}
