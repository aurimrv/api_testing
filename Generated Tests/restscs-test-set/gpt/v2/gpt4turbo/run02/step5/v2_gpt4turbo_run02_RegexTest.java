
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
import java.net.URLEncoder;

public class v2_gpt4turbo_run02_RegexTest {

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
        String encodedUrl = URLEncoder.encode("http://example.com/a/b", "UTF-8");
        String response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", encodedUrl)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(is("url"))
            .extract()
            .asString();

        assertEquals("url", response);
    }

    @Test
    public void testSubjectWithDatePattern() {
        String response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "mon01jan")
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(is("date"))
            .extract()
            .asString();

        assertEquals("date", response);
    }

    @Test
    public void testSubjectWithFpePattern() {
        String response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "12.34e+56")
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(is("fpe"))
            .extract()
            .asString();

        assertEquals("fpe", response);
    }

    @Test
    public void testSubjectWithInvalidPattern() {
        String encodedUrl = URLEncoder.encode("invalid input", "UTF-8");
        String response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", encodedUrl)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(is("none"))
            .extract()
            .asString();

        assertEquals("none", response);
    }

    @Test
    public void testServerErrorHandling() {
        String encodedUrl = URLEncoder.encode("cause error", "UTF-8");
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", encodedUrl)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(isOneOf(500, 404));
    }
}
