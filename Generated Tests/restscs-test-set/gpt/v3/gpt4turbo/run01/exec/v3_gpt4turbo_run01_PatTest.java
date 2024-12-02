
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

public class v3_gpt4turbo_run01_PatTest {
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
    public void testPatSimpleMatch() throws Exception {
        String txt = URLEncoder.encode("hello world", "UTF-8");
        String pat = URLEncoder.encode("world", "UTF-8");
        ValidatableResponse response = given().when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}", txt, pat)
            .then().statusCode(200);

        response.body(equalTo("1"));
    }

    @Test
    public void testPatReverseMatch() throws Exception {
        String txt = URLEncoder.encode("hello dlrow", "UTF-8");
        String pat = URLEncoder.encode("world", "UTF-8");
        ValidatableResponse response = given().when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}", txt, pat)
            .then().statusCode(200);

        response.body(equalTo("2"));
    }

    @Test
    public void testPatPalindromeMatch() throws Exception {
        String txt = URLEncoder.encode("hello worlddlrow", "UTF-8");
        String pat = URLEncoder.encode("world", "UTF-8");
        ValidatableResponse response = given().when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}", txt, pat)
            .then().statusCode(200);

        response.body(equalTo("4"));
    }

    @Test
    public void testPatNoMatch() throws Exception {
        String txt = URLEncoder.encode("hello planet", "UTF-8");
        String pat = URLEncoder.encode("world", "UTF-8");
        ValidatableResponse response = given().when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}", txt, pat)
            .then().statusCode(200);

        response.body(equalTo("0"));
    }

    @Test
    public void testPatError500() throws Exception {
        String txt = URLEncoder.encode("", "UTF-8");
        String pat = URLEncoder.encode("world", "UTF-8");
        given().when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}", txt, pat)
            .then().statusCode(500);
    }
}
