
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

public class v0_gpt4turbo_run03_Text2TxtTest {
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
    public void testSubjectWithTwo() {
        String word1 = "two";
        ValidatableResponse response = given().pathParam("word1", word1)
                                              .when().get(baseUrlOfSut + "/api/text2txt/{word1}")
                                              .then().statusCode(200)
                                              .body(equalTo("2"));
    }

    @Test
    public void testSubjectWithYou() {
        String word1 = "you";
        ValidatableResponse response = given().pathParam("word1", word1)
                                              .when().get(baseUrlOfSut + "/api/text2txt/{word1}")
                                              .then().statusCode(200)
                                              .body(equalTo("u"));
    }

    @Test
    public void testSubjectWithForAndFour() {
        String word1 = "for";
        ValidatableResponse response1 = given().pathParam("word1", word1)
                                               .when().get(baseUrlOfSut + "/api/text2txt/{word1}")
                                               .then().statusCode(200)
                                               .body(equalTo("4"));

        word1 = "four";
        ValidatableResponse response2 = given().pathParam("word1", word1)
                                               .when().get(baseUrlOfSut + "/api/text2txt/{word1}")
                                               .then().statusCode(200)
                                               .body(equalTo("4"));
    }

    @Test
    public void testSubjectWithAnd() {
        String word1 = "and";
        ValidatableResponse response = given().pathParam("word1", word1)
                                              .when().get(baseUrlOfSut + "/api/text2txt/{word1}")
                                              .then().statusCode(200)
                                              .body(equalTo("n"));
    }

    @Test
    public void testSubjectWithSeeYou() {
        String word1 = "see";
        String word2 = "you";
        ValidatableResponse response = given().pathParam("word1", word1)
                                              .pathParam("word2", word2)
                                              .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}")
                                              .then().statusCode(200)
                                              .body(equalTo("cu"));
    }

    @Test
    public void testSubjectWithByTheWay() {
        String word1 = "by";
        String word2 = "the";
        String word3 = "way";
        ValidatableResponse response = given().pathParam("word1", word1)
                                              .pathParam("word2", word2)
                                              .pathParam("word3", word3)
                                              .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
                                              .then().statusCode(200)
                                              .body(equalTo("btw"));
    }
}
