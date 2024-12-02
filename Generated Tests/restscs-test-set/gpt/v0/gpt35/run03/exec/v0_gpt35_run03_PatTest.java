
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

public class v0_gpt35_run03_PatTest {

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
    public void testPatEndpoint() {
        String txt = "abcdefg";
        String pat = "cde";
        given()
            .when().get(baseUrlOfSut + "/api/pat/" + txt + "/" + pat)
            .then()
            .assertThat()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseEndpoint() {
        String dayname = "Monday";
        String monthname = "January";
        given()
            .when().get(baseUrlOfSut + "/api/dateparse/" + dayname + "/" + monthname)
            .then()
            .statusCode(200);
    }

    @Test
    public void testFileSuffixEndpoint() {
        String directory = "dir";
        String file = "file.txt";
        given()
            .when().get(baseUrlOfSut + "/api/filesuffix/" + directory + "/" + file)
            .then()
            .statusCode(200);
    }

    @Test
    public void testPatUsingGETEndpoint() {
        String txt = "abcdefg";
        String pat = "cde";
        given()
            .when().get(baseUrlOfSut + "/api/pat/" + txt + "/" + pat)
            .then()
            .assertThat()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testText2TxtEndpoint() {
        String word1 = "hello";
        String word2 = "world";
        String word3 = "!";
        given()
            .when().get(baseUrlOfSut + "/api/text2txt/" + word1 + "/" + word2 + "/" + word3)
            .then()
            .statusCode(200);
    }

    @Test
    public void testTitleEndpoint() {
        String sex = "male";
        String title = "Mr.";
        given()
            .when().get(baseUrlOfSut + "/api/title/" + sex + "/" + title)
            .then()
            .statusCode(200);
    }
}
