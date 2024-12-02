
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

public class v2_gpt4o_run03_Text2TxtTest {

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
    public void testInternalServerError() {
        given().baseUri(baseUrlOfSut).when().get("/api/text2txt/invalid1/invalid2/invalid3")
               .then().statusCode(500);
    }

    @Test
    public void testText2TxtSchemaValidation() {
        given().baseUri(baseUrlOfSut)
               .when().get("/api/text2txt/see/you/soon")
               .then().statusCode(200)
               .contentType("text/plain")
               .body(equalTo("cu"));
    }

    @Test
    public void testText2TxtBusinessRules() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                                              .when().get("/api/text2txt/two/words/only")
                                              .then().statusCode(200)
                                              .body(equalTo("2"));

        String result = response.extract().asString();
        assertEquals("2", result);

        response = given().baseUri(baseUrlOfSut)
                          .when().get("/api/text2txt/by/the/way")
                          .then().statusCode(200)
                          .body(equalTo("btw"));

        result = response.extract().asString();
        assertEquals("btw", result);
    }

    @Test
    public void testText2TxtEdgeCases() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                                              .when().get("/api/text2txt/four/words/only")
                                              .then().statusCode(200)
                                              .body(equalTo("4"));

        String result = response.extract().asString();
        assertEquals("4", result);

        response = given().baseUri(baseUrlOfSut)
                          .when().get("/api/text2txt/you/are/great")
                          .then().statusCode(200)
                          .body(equalTo("u"));

        result = response.extract().asString();
        assertEquals("u", result);

        response = given().baseUri(baseUrlOfSut)
                          .when().get("/api/text2txt/and/so/it")
                          .then().statusCode(200)
                          .body(equalTo("n"));

        result = response.extract().asString();
        assertEquals("n", result);

        response = given().baseUri(baseUrlOfSut)
                          .when().get("/api/text2txt/are/you/there")
                          .then().statusCode(200)
                          .body(equalTo("r"));

        result = response.extract().asString();
        assertEquals("r", result);
    }
}
