
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

public class v3_gpt4turbo_run01_RegexTest {

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
    public void testSubjectWithValidUrl() {
        String input = "http://example.com/test";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .queryParam("txt", input)
            .when()
            .get("/api/pat")
            .then()
            .statusCode(200)
            .body(equalTo("url"));

        String result = response.extract().body().asString();
        assertEquals("url", result);
    }

    @Test
    public void testSubjectWithValidDate() {
        String input = "mon12jan";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("date"));

        String result = response.extract().body().asString();
        assertEquals("date", result);
    }

    @Test
    public void testSubjectWithValidFpe() {
        String input = "12.34e+56";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("fpe"));

        String result = response.extract().body().asString();
        assertEquals("fpe", result);
    }

    @Test
    public void testSubjectWithInvalidInput() {
        String input = "invalid_input";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("none"));

        String result = response.extract().body().asString();
        assertEquals("none", result);
    }

    @Test
    public void testSubjectWithServerErrorSimulation() {
        String input = "12/34/5678"; // Intentionally incorrect format to cause potential server error
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(anyOf(is(500), is(404))); // Checking for server error or not found
    }
}
