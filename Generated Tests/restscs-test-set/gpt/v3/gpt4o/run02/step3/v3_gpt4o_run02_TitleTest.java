
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class v3_gpt4o_run02_TitleTest {

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
    public void testValidTitles() {
        given().pathParam("sex", "male").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("1"));

        given().pathParam("sex", "female").pathParam("title", "mrs")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("0"));

        given().pathParam("sex", "none").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("2"));
    }

    @Test
    public void testInvalidTitles() {
        given().pathParam("sex", "male").pathParam("title", "mrs")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));

        given().pathParam("sex", "female").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));

        given().pathParam("sex", "none").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));
    }

    @Test
    public void testInvalidInput() {
        given().pathParam("sex", "unknown").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));

        given().pathParam("sex", "").pathParam("title", "")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("-1"));
    }

    @Test
    public void test500InternalServerError() {
        given().pathParam("sex", "male").pathParam("title", "error")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        given().pathParam("sex", "male").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .contentType("application/json")
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/titleSchema.json"));
    }

    @Test
    public void testBusinessRules() {
        given().pathParam("sex", "male").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("1"));

        given().pathParam("sex", "female").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("0"));

        given().pathParam("sex", "none").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200).body(equalTo("2"));
    }
}
