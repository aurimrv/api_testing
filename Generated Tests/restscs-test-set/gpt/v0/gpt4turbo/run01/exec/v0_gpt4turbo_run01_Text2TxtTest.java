
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

public class v0_gpt4turbo_run01_Text2TxtTest {

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
    public void testText2TxtWord1Two() {
        String path = "/api/text2txt/two/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("2"));
    }

    @Test
    public void testText2TxtWord1For() {
        String path = "/api/text2txt/for/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("4"));
    }

    @Test
    public void testText2TxtWord1Four() {
        String path = "/api/text2txt/four/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("4"));
    }

    @Test
    public void testText2TxtWord1You() {
        String path = "/api/text2txt/you/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("u"));
    }

    @Test
    public void testText2TxtWord1And() {
        String path = "/api/text2txt/and/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("n"));
    }

    @Test
    public void testText2TxtWord1Are() {
        String path = "/api/text2txt/are/any/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("r"));
    }

    @Test
    public void testText2TxtWord1SeeWord2You() {
        String path = "/api/text2txt/see/you/any";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("cu"));
    }

    @Test
    public void testText2TxtWord1ByWord2TheWord3Way() {
        String path = "/api/text2txt/by/the/way";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("btw"));
    }

    @Test
    public void testText2TxtWordsNotMapped() {
        String path = "/api/text2txt/hello/world/test";

        given().
            baseUri(baseUrlOfSut).
        when().
            get(path).
        then().
            statusCode(200).
            body(equalTo("")); // Expected empty result as input words are not mapped.
    }
}
