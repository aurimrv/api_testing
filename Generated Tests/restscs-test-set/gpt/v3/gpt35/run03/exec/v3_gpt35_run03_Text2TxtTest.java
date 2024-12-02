
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

public class v3_gpt35_run03_Text2TxtTest {

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
    public void testText2Txt_ValidInput_ReturnsAbbreviation() {
        String word1 = "two";
        String word2 = "for";
        String word3 = "you";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/text2txt/" + word1 + "/" + word2 + "/" + word3)
                .when().get()
                .then().statusCode(200);

        String result = response.extract().asString();
        assertEquals("2", result);
    }

    @Test
    public void testText2Txt_InvalidInput_ReturnsEmptyString() {
        String word1 = "invalid";
        String word2 = "input";
        String word3 = "test";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/text2txt/" + word1 + "/" + word2 + "/" + word3)
                .when().get()
                .then().statusCode(200);

        String result = response.extract().asString();
        assertEquals("", result);
    }

    @Test
    public void testText2Txt_ServerError_Returns500() {
        String word1 = "error";
        String word2 = "server";
        String word3 = "test";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/text2txt/" + word1 + "/" + word2 + "/" + word3)
                .when().get()
                .then().statusCode(500);
    }

}
