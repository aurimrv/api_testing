
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

public class v3_gpt4turbo_run03_Text2TxtTest {
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
    public void testSubjectWithValidInputs() {
        String word1 = "you";
        String word2 = "are";
        String word3 = "great";

        ValidatableResponse response = given().pathParam("word1", word1)
                                              .pathParam("word2", word2)
                                              .pathParam("word3", word3)
                                              .when()
                                              .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
                                              .then()
                                              .statusCode(200)
                                              .body(equalTo("u"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithInvalidInput() {
        String word1 = "inv@lid";
        String word2 = "input";
        String word3 = "test";

        given().pathParam("word1", word1)
               .pathParam("word2", word2)
               .pathParam("word3", word3)
               .when()
               .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then()
               .statusCode(500); // Expecting Internal Server Error due to invalid input handling
    }

    @Test
    public void testSubjectWithBoundaryConditions() {
        // Testing boundary input conditions
        String word1 = ""; // Empty string as input
        String word2 = "you";
        String word3 = "see";

        given().pathParam("word1", word1)
               .pathParam("word2", word2)
               .pathParam("word3", word3)
               .when()
               .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then()
               .statusCode(200)
               .body(equalTo("")); // Expected response should handle empty string correctly
    }
}
