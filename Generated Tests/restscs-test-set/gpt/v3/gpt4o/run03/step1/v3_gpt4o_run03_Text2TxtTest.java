
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

public class v3_gpt4o_run03_Text2TxtTest {

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
    public void testText2TxtValidInput() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/you/are/the")
            .then()
            .statusCode(200)
            .body(equalTo("u"));

        res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/by/the/way")
            .then()
            .statusCode(200)
            .body(equalTo("btw"));

        res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/two/two/two")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testText2TxtInvalidInput() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/invalid/word/input")
            .then()
            .statusCode(200)
            .body(equalTo(""));

        res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/see/you/invalid")
            .then()
            .statusCode(200)
            .body(equalTo("cu"));
    }

    @Test
    public void testText2TxtServerError() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/four/four")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/you/are/the")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("text2txtSchema.json"));
    }

    @Test
    public void testBusinessRules() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/for/for/for")
            .then()
            .statusCode(200)
            .body(equalTo("4"));

        res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/see/you/again")
            .then()
            .statusCode(200)
            .body(equalTo("cu"));
    }

    @Test
    public void testNotFoundResponse() {
        ValidatableResponse res = given()
            .baseUri(baseUrlOfSut)
            .get("/api/text2txt/unknown/endpoint")
            .then()
            .statusCode(404);
    }
}
