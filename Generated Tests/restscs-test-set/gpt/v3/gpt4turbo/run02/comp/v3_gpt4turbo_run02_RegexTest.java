
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

public class v3_gpt4turbo_run02_RegexTest {
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
    public void testSubjectWithUrl() {
        String input = "http://example.com/path/to/resource";
        ValidatableResponse response = given().baseUri(baseUrlOfSut).basePath("/api/pat/{txt}")
            .pathParam("txt", input)
            .get()
            .then()
            .statusCode(200)
            .body(is("url"));
    }

    @Test
    public void testSubjectWithDate() {
        String input = "tue23dec";
        ValidatableResponse response = given().baseUri(baseUrlOfSut).basePath("/api/pat/{txt}")
            .pathParam("txt", input)
            .get()
            .then()
            .statusCode(200)
            .body(is("date"));
    }

    @Test
    public void testSubjectWithFpe() {
        String input = "12.34e+56";
        ValidatableResponse response = given().baseUri(baseUrlOfSut).basePath("/api/pat/{txt}")
            .pathParam("txt", input)
            .get()
            .then()
            .statusCode(200)
            .body(is("fpe"));
    }

    @Test
    public void testSubjectWithNone() {
        String input = "hello";
        ValidatableResponse response = given().baseUri(baseUrlOfSut).basePath("/api/pat/{txt}")
            .pathParam("txt", input)
            .get()
            .then()
            .statusCode(200)
            .body(is("none"));
    }

    @Test
    public void testServerErrorSimulation() {
        String input = "simulateServerError";
        given().baseUri(baseUrlOfSut).basePath("/api/pat/{txt}")
            .pathParam("txt", input)
            .get()
            .then()
            .statusCode(500);
    }
}
