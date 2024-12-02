
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

public class v1_gpt4turbo_run01_RegexTest {

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
    public void testRegexUrlMatch() {
        String testUrl = "http://user/path";
        String response = given().baseUri(baseUrlOfSut)
                        .basePath("/api/pat/{txt}")
                        .pathParam("txt", testUrl)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertEquals("url", response);
    }

    @Test
    public void testRegexDateMatch() {
        String testDate = "mon01jan";
        String response = given().baseUri(baseUrlOfSut)
                        .basePath("/api/pat/{txt}")
                        .pathParam("txt", testDate)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertEquals("date", response);
    }

    @Test
    public void testRegexFpeMatch() {
        String testFpe = "12.34e+56";
        String response = given().baseUri(baseUrlOfSut)
                        .basePath("/api/pat/{txt}")
                        .pathParam("txt", testFpe)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertEquals("fpe", response);
    }

    @Test
    public void testRegexNoneMatch() {
        String testNone = "not a pattern";
        String response = given().baseUri(baseUrlOfSut)
                        .basePath("/api/pat/{txt}")
                        .pathParam("txt", testNone)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();
        assertEquals("none", response);
    }
}
