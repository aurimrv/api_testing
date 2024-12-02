
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

public class v0_gpt4o_run01_RegexTest {

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
    public void testRegexUrl() {
        String txt = "http//:example_com";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testRegexDate() {
        String txt = "mon01jan";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("date"));
    }

    @Test
    public void testRegexFpe() {
        String txt = "12.34e+56";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("fpe"));
    }

    @Test
    public void testRegexNone() {
        String txt = "someRandomText";
        ValidatableResponse response = given()
            .pathParam("txt", txt)
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testCalcAddition() {
        ValidatableResponse response = given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("0.0"));
    }

    @Test
    public void testCalcSubtraction() {
        ValidatableResponse response = given()
            .pathParam("op", "sub")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("0.0"));
    }

    @Test
    public void testCalcMultiplication() {
        ValidatableResponse response = given()
            .pathParam("op", "mul")
            .pathParam("arg1", 2)
            .pathParam("arg2", 4)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("0.0"));
    }

    @Test
    public void testCalcDivision() {
        ValidatableResponse response = given()
            .pathParam("op", "div")
            .pathParam("arg1", 10)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("0.0"));
    }

    @Test
    public void testCalcNotFound() {
        ValidatableResponse response = given()
            .pathParam("op", "mod")
            .pathParam("arg1", 10)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);
    }

    @Test
    public void testCookie() {
        ValidatableResponse response = given()
            .pathParam("name", "session")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParse() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testFileSuffix() {
        ValidatableResponse response = given()
            .pathParam("directory", "docs")
            .pathParam("file", "report.txt")
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testNotypevar() {
        ValidatableResponse response = given()
            .pathParam("i", 5)
            .pathParam("s", "example")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testOrdered4() {
        ValidatableResponse response = given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("z", "three")
            .pathParam("y", "four")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testText2txt() {
        ValidatableResponse response = given()
            .pathParam("word1", "foo")
            .pathParam("word2", "bar")
            .pathParam("word3", "baz")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(equalTo(""));
    }

    @Test
    public void testTitle() {
        ValidatableResponse response = given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }
}
