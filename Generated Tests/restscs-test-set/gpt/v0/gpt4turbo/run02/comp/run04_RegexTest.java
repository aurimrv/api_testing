
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

public class run04_RegexTest {
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
    public void testSubjectWithUrlPattern() {
        String urlSample = "http://example.com/a/b";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", urlSample)
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("url"));
    }

    @Test
    public void testSubjectWithDatePattern() {
        String dateSample = "mon01jan";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", dateSample)
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("date"));
    }

    @Test
    public void testSubjectWithFpePattern() {
        String fpeSample = "12.34e+56";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", fpeSample)
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("fpe"));
    }

    @Test
    public void testSubjectWithNoMatch() {
        String noMatchSample = "randomString";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", noMatchSample)
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("none"));
    }
}
