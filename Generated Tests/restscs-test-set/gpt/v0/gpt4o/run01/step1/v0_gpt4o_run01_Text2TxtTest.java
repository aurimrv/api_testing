
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

public class v0_gpt4o_run01_Text2TxtTest {

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
    public void testText2Txt() {
        // Test case for "two"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "two")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("2"));

        // Test case for "for"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "for")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("4"));

        // Test case for "four"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "four")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("4"));

        // Test case for "you"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "you")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("u"));

        // Test case for "and"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "and")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("n"));

        // Test case for "are"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "are")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("r"));

        // Test case for "see you"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "see")
            .pathParam("word2", "you")
            .pathParam("word3", "")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("cu"));

        // Test case for "by the way"
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "by")
            .pathParam("word2", "the")
            .pathParam("word3", "way")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("btw"));

        // Test case for non-matching words
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "example")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo(""));
    }
}
