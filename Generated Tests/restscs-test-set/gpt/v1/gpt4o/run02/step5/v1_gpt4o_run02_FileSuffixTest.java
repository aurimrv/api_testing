
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

public class v1_gpt4o_run02_FileSuffixTest {

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
    public void testFileSuffixEndpoint() {
        // Valid cases
        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/text/sample.txt")
            .then().statusCode(200).body(equalTo("0"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/acrobat/sample.pdf")
            .then().statusCode(200).body(equalTo("1"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/word/sample.doc")
            .then().statusCode(200).body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/bin/sample.exe")
            .then().statusCode(200).body(equalTo("3"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/lib/sample.dll")
            .then().statusCode(200).body(equalTo("4"));

        // Invalid cases
        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/text/sample.pdf")
            .then().statusCode(200).body(equalTo("5"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/word/sample.txt")
            .then().statusCode(200).body(equalTo("5"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/unknown/sample.txt")
            .then().statusCode(200).body(equalTo("5"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/bin/sample.dll")
            .then().statusCode(200).body(equalTo("5"));
    }
}
