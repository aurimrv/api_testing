
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

public class v0_gpt4o_run02_FileSuffixTest {

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
        // Test for 'text.txt'
        given().pathParam("directory", "text").pathParam("file", "text.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("1"));

        // Test for 'acrobat.pdf'
        given().pathParam("directory", "acrobat").pathParam("file", "acrobat.pdf")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("2"));

        // Test for 'word.doc'
        given().pathParam("directory", "word").pathParam("file", "word.doc")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("3"));

        // Test for 'bin.exe'
        given().pathParam("directory", "bin").pathParam("file", "bin.exe")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("4"));

        // Test for 'lib.dll'
        given().pathParam("directory", "lib").pathParam("file", "lib.dll")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("5"));

        // Test for unsupported suffix
        given().pathParam("directory", "text").pathParam("file", "unsupported.pdf")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("0"));

        // Test for unsupported directory
        given().pathParam("directory", "unknown").pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("0"));

        // Test for file with no suffix
        given().pathParam("directory", "text").pathParam("file", "file")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("0"));

        // Test for file with multiple dots
        given().pathParam("directory", "text").pathParam("file", "file.name.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200).body(equalTo("1"));
    }

    @Test
    public void testFileSuffixInvalidStatusCodes() {
        // Test for invalid method
        given().pathParam("directory", "text").pathParam("file", "file.txt")
            .when().post(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(405);

        // Test for missing parameters
        given().pathParam("directory", "text")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/")
            .then().statusCode(404);
    }
}
