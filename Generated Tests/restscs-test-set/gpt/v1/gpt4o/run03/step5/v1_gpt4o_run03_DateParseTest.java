
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

public class v1_gpt4o_run03_DateParseTest {

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
    public void testDateParse_ValidDayAndMonth() {
        ValidatableResponse res = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParse_ValidDayInvalidMonth() {
        ValidatableResponse res = given()
            .pathParam("dayname", "tue")
            .pathParam("monthname", "abc")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParse_InvalidDayValidMonth() {
        ValidatableResponse res = given()
            .pathParam("dayname", "abc")
            .pathParam("monthname", "feb")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParse_InvalidDayAndMonth() {
        ValidatableResponse res = given()
            .pathParam("dayname", "abc")
            .pathParam("monthname", "xyz")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testCalc_Addition() {
        ValidatableResponse res = given()
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
    public void testCalc_Subtraction() {
        ValidatableResponse res = given()
            .pathParam("op", "sub")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalc_Multiplication() {
        ValidatableResponse res = given()
            .pathParam("op", "mul")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("6.0"));
    }

    @Test
    public void testCalc_Division() {
        ValidatableResponse res = given()
            .pathParam("op", "div")
            .pathParam("arg1", 6)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalc_InvalidOperation() {
        ValidatableResponse res = given()
            .pathParam("op", "mod")
            .pathParam("arg1", 6)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testCookie() {
        ValidatableResponse res = given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "example")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(containsString("session=12345"));
    }

    @Test
    public void testCostfuns() {
        ValidatableResponse res = given()
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(containsString("cost"));
    }

    @Test
    public void testFileSuffix() {
        ValidatableResponse res = given()
            .pathParam("directory", "docs")
            .pathParam("file", "test.txt")
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(200)
            .body(containsString(".txt"));
    }

    @Test
    public void testNotyPevar() {
        ValidatableResponse res = given()
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(containsString("notypevar"));
    }

    @Test
    public void testOrdered4() {
        ValidatableResponse res = given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(containsString("ordered"));
    }

    @Test
    public void testRegex() {
        ValidatableResponse res = given()
            .pathParam("txt", "example")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(containsString("regex"));
    }

    @Test
    public void testPat() {
        ValidatableResponse res = given()
            .pathParam("txt", "example")
            .pathParam("pat", "ex.*")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(containsString("pattern"));
    }

    @Test
    public void testText2txt() {
        ValidatableResponse res = given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(containsString("text2txt"));
    }

    @Test
    public void testTitle() {
        ValidatableResponse res = given()
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(containsString("title"));
    }
}
