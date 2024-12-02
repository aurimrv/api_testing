
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

public class v3_gpt4turbo_run02_PatTest {

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
    public void testSubjectNormalCase() {
        String response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abcdefgh")
            .pathParam("pat", "def")
            .when().get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(is("1"))
            .extract().asString();

        assertEquals("1", response);
    }

    @Test
    public void testSubjectReverseCase() {
        String response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abcdefgh")
            .pathParam("pat", "fed")
            .when().get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(is("2"))
            .extract().asString();

        assertEquals("2", response);
    }

    @Test
    public void testSubjectBothCases() {
        String response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abcdefgfedcba")
            .pathParam("pat", "def")
            .when().get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(is("3"))
            .extract().asString();

        assertEquals("3", response);
    }

    @Test
    public void testSubjectPalindromeCase() {
        String response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "defgfed")
            .pathParam("pat", "def")
            .when().get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(is("0"))
            .extract().asString();

        assertEquals("0", response);
    }

    @Test
    public void testSubjectInvalidInput() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abc")
            .pathParam("pat", "de")
            .when().get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
    }
}
