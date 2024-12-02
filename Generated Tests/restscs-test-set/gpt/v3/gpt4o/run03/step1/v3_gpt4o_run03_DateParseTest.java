
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

public class v3_gpt4o_run03_DateParseTest {

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
    public void testDateParseValidInput() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParseInvalidDay() {
        given()
            .pathParam("dayname", "xyz")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidMonth() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "xyz")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidDayAndMonth() {
        given()
            .pathParam("dayname", "xyz")
            .pathParam("monthname", "xyz")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testDateParseInternalServerError() {
        given()
            .pathParam("dayname", "")
            .pathParam("monthname", "")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCalcValidOperation() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testCalcDivisionByZero() {
        given()
            .pathParam("op", "div")
            .pathParam("arg1", 1)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCalcInvalidOperation() {
        given()
            .pathParam("op", "unknown")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieValidInput() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "123")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testCookieInvalidInput() {
        given()
            .pathParam("name", "")
            .pathParam("val", "")
            .pathParam("site", "")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCostfunsValidInput() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testCostfunsInvalidInput() {
        given()
            .pathParam("i", -1)
            .pathParam("s", "")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixValidInput() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testFileSuffixInvalidInput() {
        given()
            .pathParam("directory", "")
            .pathParam("file", "")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testNotyPevarValidInput() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testNotyPevarInvalidInput() {
        given()
            .pathParam("i", -1)
            .pathParam("s", "")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testOrdered4ValidInput() {
        given()
            .pathParam("w", "word1")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testOrdered4InvalidInput() {
        given()
            .pathParam("w", "")
            .pathParam("x", "")
            .pathParam("y", "")
            .pathParam("z", "")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testRegexValidInput() {
        given()
            .pathParam("txt", "test")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testRegexInvalidInput() {
        given()
            .pathParam("txt", "")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testPatValidInput() {
        given()
            .pathParam("txt", "test")
            .pathParam("pat", "pat")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testPatInvalidInput() {
        given()
            .pathParam("txt", "")
            .pathParam("pat", "")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testText2txtValidInput() {
        given()
            .pathParam("word1", "word1")
            .pathParam("word2", "word2")
            .pathParam("word3", "word3")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testText2txtInvalidInput() {
        given()
            .pathParam("word1", "")
            .pathParam("word2", "")
            .pathParam("word3", "")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testTitleValidInput() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test
    public void testTitleInvalidInput() {
        given()
            .pathParam("sex", "")
            .pathParam("title", "")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(500);
    }
}
