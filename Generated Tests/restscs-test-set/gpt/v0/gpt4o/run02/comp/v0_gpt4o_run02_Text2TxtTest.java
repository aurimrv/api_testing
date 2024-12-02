
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

public class v0_gpt4o_run02_Text2TxtTest {

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
    public void testText2Txt() {
        // Test case 1: "two" -> "2"
        given().get(baseUrlOfSut + "/api/text2txt/two/any/any")
            .then().statusCode(200)
            .body(equalTo("2"));

        // Test case 2: "for" -> "4"
        given().get(baseUrlOfSut + "/api/text2txt/for/any/any")
            .then().statusCode(200)
            .body(equalTo("4"));

        // Test case 3: "four" -> "4"
        given().get(baseUrlOfSut + "/api/text2txt/four/any/any")
            .then().statusCode(200)
            .body(equalTo("4"));

        // Test case 4: "you" -> "u"
        given().get(baseUrlOfSut + "/api/text2txt/you/any/any")
            .then().statusCode(200)
            .body(equalTo("u"));

        // Test case 5: "and" -> "n"
        given().get(baseUrlOfSut + "/api/text2txt/and/any/any")
            .then().statusCode(200)
            .body(equalTo("n"));

        // Test case 6: "are" -> "r"
        given().get(baseUrlOfSut + "/api/text2txt/are/any/any")
            .then().statusCode(200)
            .body(equalTo("r"));

        // Test case 7: "see you" -> "cu"
        given().get(baseUrlOfSut + "/api/text2txt/see/you/any")
            .then().statusCode(200)
            .body(equalTo("cu"));

        // Test case 8: "by the way" -> "btw"
        given().get(baseUrlOfSut + "/api/text2txt/by/the/way")
            .then().statusCode(200)
            .body(equalTo("btw"));

        // Test case 9: Unabbreviated words
        given().get(baseUrlOfSut + "/api/text2txt/hello/world/test")
            .then().statusCode(200)
            .body(equalTo(""));
    }
}
