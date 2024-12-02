
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

public class v0_gpt4o_run01_FileSuffixTest {

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
    public void testFileSuffix() {
        // Valid file suffixes
        testFileSuffix("text", "file.txt", "1");
        testFileSuffix("acrobat", "file.pdf", "2");
        testFileSuffix("word", "file.doc", "3");
        testFileSuffix("bin", "file.exe", "4");
        testFileSuffix("lib", "file.dll", "5");

        // Invalid file suffixes
        testFileSuffix("text", "file.pdf", "0");
        testFileSuffix("acrobat", "file.doc", "0");
        testFileSuffix("word", "file.exe", "0");
        testFileSuffix("bin", "file.dll", "0");
        testFileSuffix("lib", "file.txt", "0");

        // Invalid directories
        testFileSuffix("music", "file.mp3", "0");
        testFileSuffix("video", "file.mp4", "0");
        testFileSuffix("image", "file.png", "0");

        // Edge cases
        testFileSuffix("text", "file", "0");
        testFileSuffix("text", "file.", "0");
        testFileSuffix("text", ".txt", "0");
    }

    private void testFileSuffix(String directory, String file, String expected) {
        given()
            .pathParam("directory", directory)
            .pathParam("file", file)
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo(expected));
    }

    @Test
    public void testFileSuffixInvalidMethods() {
        testInvalidMethod("PUT", "text", "file.txt");
        testInvalidMethod("POST", "text", "file.txt");
        testInvalidMethod("DELETE", "text", "file.txt");
    }

    private void testInvalidMethod(String method, String directory, String file) {
        given()
            .pathParam("directory", directory)
            .pathParam("file", file)
        .when()
            .request(method, baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(405);  // Method Not Allowed
    }

    @Test
    public void testFileSuffixInvalidPaths() {
        given()
            .pathParam("directory", "text")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/")
        .then()
            .statusCode(404);  // Not Found

        given()
            .pathParam("directory", "text")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}")
        .then()
            .statusCode(404);  // Not Found
    }
}
