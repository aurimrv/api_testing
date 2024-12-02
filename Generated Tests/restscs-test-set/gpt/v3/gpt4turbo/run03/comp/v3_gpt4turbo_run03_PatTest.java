
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

public class v3_gpt4turbo_run03_PatTest {

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
    public void testSubjectWithValidPattern() {
        String txt = "abcde";
        String pat = "bcd";
        given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testSubjectWithReversePattern() {
        String txt = "abcde";
        String pat = "cba";
        given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testSubjectWithInvalidPattern() {
        String txt = "abcde";
        String pat = "xyz";
        given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testSubjectWithError() {
        String txt = "abcde";
        String pat = ""; // Empty pattern to potentially trigger an error
        given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(anyOf(is(500), is(400))); // Expecting either 400 Bad Request or 500 Internal Server Error
    }
}
