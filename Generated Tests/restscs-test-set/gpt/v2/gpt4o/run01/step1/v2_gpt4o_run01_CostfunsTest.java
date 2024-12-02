
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

public class v2_gpt4o_run01_CostfunsTest {

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
    public void testCostfunsValidInputs() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "baab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCostfunsInvalidInputs() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "baab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testCostfunsInternalError() {
        given()
            .pathParam("i", Integer.MAX_VALUE)
            .pathParam("s", "baab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCostfunsSchemaValidation() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("costfuns-schema.json"));
    }

    @Test
    public void testCostfunsBusinessRule() {
        given()
            .pathParam("i", 666)
            .pathParam("s", "ababab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCalcEndpoint() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 1)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCookieEndpoint() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "abcd1234")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("session=abcd1234; Domain=example.com"));
    }

    @Test
    public void testDateParseEndpoint() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("Parsed date: Monday January"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .pathParam("directory", "src")
            .pathParam("file", "main.java")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("main.java"));
    }

    @Test
    public void testRegexEndpoint() {
        given()
            .pathParam("txt", "hello")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("Pattern matched: hello"));
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title: Mr, Sex: male"));
    }
}
