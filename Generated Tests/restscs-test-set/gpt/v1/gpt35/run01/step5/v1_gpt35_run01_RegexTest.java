
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

public class v1_gpt35_run01_RegexTest {

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
    public void testRegexEndpoint() {
        String txt = "https://www.example.com";
        given()
            .pathParam("txt", txt)
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testCalcEndpoint() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 5)
            .pathParam("arg2", 10)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("0.0"));
    }

    @Test
    public void testDateParseEndpoint() {
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
    public void testPatternEndpoint() {
        String pat = "[a-zA-Z]+";
        String txt = "HelloWorld";
        given()
            .pathParam("pat", pat)
            .pathParam("txt", txt)
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("HelloWorld"));
    }

    @Test
    public void testText2TxtEndpoint() {
        given()
            .pathParam("word1", "Hello")
            .pathParam("word2", "World")
            .pathParam("word3", "!")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Hello World!"));
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "Mr")
            .pathParam("title", "Smith")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr Smith"));
    }
}
