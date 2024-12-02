
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

public class v3_gpt4o_run01_Ordered4Test {

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
    public void testOrdered4Increasing() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        given()
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "apple")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "date")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidLength() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", "cherrylong")
            .pathParam("y", "banana")
            .pathParam("z", "date")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{y}/{z}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testCalcSuccess() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("8.0"));
    }

    @Test
    public void testCalcInvalidOperation() {
        given()
            .pathParam("op", "invalidOp")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testCookie() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testCostfuns() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "example")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("Cost function result"));
    }

    @Test
    public void testDateParse() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("Parsed date"));
    }

    @Test
    public void testFileSuffix() {
        given()
            .pathParam("directory", "dir")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("File suffix"));
    }

    @Test
    public void testRegex() {
        given()
            .pathParam("txt", "sampletext")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("Regex result"));
    }

    @Test
    public void testPat() {
        given()
            .pathParam("txt", "sampletext")
            .pathParam("pat", "pattern")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Pattern result"));
    }

    @Test
    public void testText2Txt() {
        given()
            .pathParam("word1", "word1")
            .pathParam("word2", "word2")
            .pathParam("word3", "word3")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Text to text result"));
    }

    @Test
    public void testTitle() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title result"));
    }

    @Test
    public void testInternalError() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", "berry")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{y}/{z}?forceError=true")
        .then()
            .statusCode(500);
    }
}
