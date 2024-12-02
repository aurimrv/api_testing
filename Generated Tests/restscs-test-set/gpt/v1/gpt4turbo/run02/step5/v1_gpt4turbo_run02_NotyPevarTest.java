
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

public class v1_gpt4turbo_run02_NotyPevarTest {
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
    public void testCalcUsingGET() {
        double arg1 = 28;
        double arg2 = 28;
        String op = "add";
        given().baseUri(baseUrlOfSut)
            .pathParam("arg1", arg1)
            .pathParam("arg2", arg2)
            .pathParam("op", op)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("56.0"));
    }

    @Test
    public void testCookieUsingGET() {
        String name = "sessionId";
        String val = "12345";
        String site = "example.com";
        given().baseUri(baseUrlOfSut)
            .pathParam("name", name)
            .pathParam("val", val)
            .pathParam("site", site)
            .when().get("/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testCostfunsUsingGET() {
        int i = 28;
        String s = "hello";
        given().baseUri(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testNotyPevarUsingGET() {
        int i = 28;
        String s = "hello";
        given().baseUri(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("3"));
    }
}
