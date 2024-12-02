
package org.restscs;

import  org.junit.AfterClass;
import  org.junit.BeforeClass;
import  org.junit.Before;
import  org.junit.Test;
import static org.junit.Assert.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;

public class v3_gpt35_run01_FileSuffixTest {

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
    public void testFileSuffix_invalidInput_shouldReturn500() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/invalidDir/invalidFile")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffix_validInput_textTxt_shouldReturn1() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/text/test.txt")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testFileSuffix_validInput_wordDoc_shouldReturn3() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/word/test.doc")
            .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testFileSuffix_validInput_acrobatPdf_shouldReturn2() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/acrobat/test.pdf")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testFileSuffix_validInput_binExe_shouldReturn4() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/bin/test.exe")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testFileSuffix_validInput_libDll_shouldReturn5() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/lib/test.dll")
            .then()
            .statusCode(200)
            .body(equalTo("5"));
    }
}
