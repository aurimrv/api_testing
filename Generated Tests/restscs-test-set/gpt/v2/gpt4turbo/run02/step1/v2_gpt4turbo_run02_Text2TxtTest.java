
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

public class v2_gpt4turbo_run02_Text2TxtTest {
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
    public void testText2TxtEndpointValidInputs() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "two")
            .pathParam("word2", "you")
            .pathParam("word3", "way")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200);

        response.assertThat().body(equalTo("2"));
    }

    @Test
    public void testText2TxtEndpointInvalidInput() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "invalid")
            .pathParam("word2", "invalid")
            .pathParam("word3", "invalid")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(404); // Assuming the API returns 404 for invalid path inputs

        response.assertThat().body(equalTo(""));
    }

    @Test
    public void testText2TxtEndpointServerError() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "null")
            .pathParam("word2", "null")
            .pathParam("word3", "null")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(500); // Simulate server error by sending null values

        response.assertThat().body(equalTo("Internal Server Error"));
    }

    @Test
    public void testText2TxtEndpointSchemaValidation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "see")
            .pathParam("word2", "you")
            .pathParam("word3", "null")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200).body(matchesJsonSchemaInClasspath("text2txt-schema.json"));

        response.assertThat().body(equalTo("cu"));
    }
}
