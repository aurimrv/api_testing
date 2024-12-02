
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

public class v0_gpt4turbo_run02_Text2TxtTest {
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
    public void testText2TxtTwo() {
        ValidatableResponse response = given().pathParam("word1", "two")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testText2TxtFor() {
        ValidatableResponse response = given().pathParam("word1", "for")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testText2TxtFour() {
        ValidatableResponse response = given().pathParam("word1", "four")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testText2TxtYou() {
        ValidatableResponse response = given().pathParam("word1", "you")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("u"));
    }

    @Test
    public void testText2TxtAnd() {
        ValidatableResponse response = given().pathParam("word1", "and")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("n"));
    }

    @Test
    public void testText2TxtAre() {
        ValidatableResponse response = given().pathParam("word1", "are")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(200)
            .body(equalTo("r"));
    }

    @Test
    public void testText2TxtSeeYou() {
        ValidatableResponse response = given().pathParam("word1", "see")
            .pathParam("word2", "you")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}")
            .then()
            .statusCode(200)
            .body(equalTo("cu"));
    }

    @Test
    public void testText2TxtNotFound() {
        ValidatableResponse response = given().pathParam("word1", "notfound")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}")
            .then()
            .statusCode(404);
    }
}
