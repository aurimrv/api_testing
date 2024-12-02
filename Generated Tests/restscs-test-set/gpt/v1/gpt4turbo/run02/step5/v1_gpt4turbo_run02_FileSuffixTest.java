
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

public class v1_gpt4turbo_run02_FileSuffixTest {

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
    public void testFileSuffixTxt() {
        given()
            .pathParam("directory", "text")
            .pathParam("file", "example.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixPdf() {
        given()
            .pathParam("directory", "acrobat")
            .pathParam("file", "example.pdf")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixDoc() {
        given()
            .pathParam("directory", "word")
            .pathParam("file", "example.doc")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixExe() {
        given()
            .pathParam("directory", "bin")
            .pathParam("file", "example.exe")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixDll() {
        given()
            .pathParam("directory", "lib")
            .pathParam("file", "example.dll")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixNoSuffix() {
        given()
            .pathParam("directory", "text")
            .pathParam("file", "example")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixUnmappedDirectory() {
        given()
            .pathParam("directory", "unmapped")
            .pathParam("file", "example.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200);
    }
}
