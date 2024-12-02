
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

public class v2_gpt4turbo_run01_Text2TxtTest {

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
    public void testText2TxtAPIWithValidInput() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "and")
            .pathParam("word2", "any")
            .pathParam("word3", "any")
            .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(is("n"));

        String result = response.extract().asString();
        assertEquals("n", result);
    }

    @Test
    public void testText2TxtAPIWithInvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "unknown")
            .pathParam("word2", "unknown")
            .pathParam("word3", "unknown")
            .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(is(""));
    }

    @Test
    public void testText2TxtAPIWithErrorScenario() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "")
            .pathParam("word2", "")
            .pathParam("word3", "")
            .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(500); // Expecting internal server error due to empty parameters
    }

    @Test
    public void testText2TxtAPIWithBoundaryInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "see")
            .pathParam("word2", "you")
            .pathParam("word3", "any")
            .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(is("cu"));
    }

    @Test
    public void testText2TxtAPIWithComplexInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "by")
            .pathParam("word2", "the")
            .pathParam("word3", "way")
            .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(is("btw"));
    }
}
