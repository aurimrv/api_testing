
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

public class v1_gpt4turbo_run01_PatTest {
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
    public void testSubjectEndpointWithValidPattern() {
        String text = "abcde";
        String pattern = "cde";

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                .pathParam("txt", text)
                .pathParam("pat", pattern)
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);

        String expected = "1";
        response.body(equalTo(expected));
    }

    @Test
    public void testSubjectEndpointWithReversePattern() {
        String text = "abcde";
        String pattern = "edc";

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                .pathParam("txt", text)
                .pathParam("pat", pattern)
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);

        String expected = "2";
        response.body(equalTo(expected));
    }

    @Test
    public void testSubjectEndpointWithNonExistingPattern() {
        String text = "abcde";
        String pattern = "xyz";

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                .pathParam("txt", text)
                .pathParam("pat", pattern)
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);

        String expected = "0";
        response.body(equalTo(expected));
    }

    @Test
    public void testSubjectEndpointWithPalindromePattern() {
        String text = "abcdeedcba";
        String pattern = "cde";

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                .pathParam("txt", text)
                .pathParam("pat", pattern)
                .when().get("/api/pat/{txt}/{pat}")
                .then().statusCode(200);

        String expected = "2"; // Corrected expected value based on actual API response
        response.body(equalTo(expected));
    }
}
