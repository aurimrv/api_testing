
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

public class v1_gpt4turbo_run01_Text2TxtTest {

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
    public void testText2TxtSingleWordTransformations() {
        given().pathParam("word1", "two").queryParam("word2", "").queryParam("word3", "")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("2"));

        given().pathParam("word1", "for").queryParam("word2", "").queryParam("word3", "")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("4"));

        given().pathParam("word1", "you").queryParam("word2", "").queryParam("word3", "")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("u"));
    }

    @Test
    public void testText2TxtMultipleWordTransformations() {
        given().pathParam("word1", "see").queryParam("word2", "you").queryParam("word3", "")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("cu"));

        given().pathParam("word1", "by").queryParam("word2", "the").queryParam("word3", "way")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(equalTo("btw"));
    }

    @Test
    public void testText2TxtNonExistWordTransformations() {
        given().pathParam("word1", "hello").queryParam("word2", "world").queryParam("word3", "test")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200);
    }
}
