
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

public class v3_gpt4o_run01_TitleTest {

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
    public void testTitleEndpointValidMale() {
        given().pathParam("sex", "male").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("1"));
    }

    @Test
    public void testTitleEndpointValidFemale() {
        given().pathParam("sex", "female").pathParam("title", "mrs")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("0"));
    }

    @Test
    public void testTitleEndpointValidNone() {
        given().pathParam("sex", "none").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("2"));
    }

    @Test
    public void testTitleEndpointInvalidSex() {
        given().pathParam("sex", "unknown").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));
    }

    @Test
    public void testTitleEndpointInvalidTitle() {
        given().pathParam("sex", "male").pathParam("title", "unknown")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));
    }

    @Test
    public void testTitleEndpointInternalServerError() {
        given().pathParam("sex", "none").pathParam("title", "invalid")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(500);
    }

    @Test
    public void testTitleEndpointSchemaValidation() {
        given().pathParam("sex", "female").pathParam("title", "lady")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(matchesPattern("^-?[0-2]$"));
    }

    @Test
    public void testTitleEndpointUnauthorized() {
        // Assuming authorization is required for this endpoint in a real scenario
        given().auth().none().pathParam("sex", "female").pathParam("title", "ms")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(401);
    }

    @Test
    public void testTitleEndpointForbidden() {
        // Assuming certain users are forbidden in a real scenario
        given().auth().basic("forbiddenUser", "password").pathParam("sex", "female").pathParam("title", "ms")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(403);
    }

    @Test
    public void testTitleEndpointNotFound() {
        given().pathParam("sex", "female").pathParam("title", "notexists")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(404);
    }
}
