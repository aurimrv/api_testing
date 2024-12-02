
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

public class v0_gpt4turbo_run01_FileSuffixTest {
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
    public void testFileSuffixEndpoint_txt() {
        given().pathParam("directory", "text").pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_pdf() {
        given().pathParam("directory", "acrobat").pathParam("file", "file.pdf")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_doc() {
        given().pathParam("directory", "word").pathParam("file", "file.doc")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_exe() {
        given().pathParam("directory", "bin").pathParam("file", "file.exe")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_dll() {
        given().pathParam("directory", "lib").pathParam("file", "file.dll")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_noSuffix() {
        given().pathParam("directory", "lib").pathParam("file", "file")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint_invalidDirectory() {
        given().pathParam("directory", "unknown").pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200);
    }
}
