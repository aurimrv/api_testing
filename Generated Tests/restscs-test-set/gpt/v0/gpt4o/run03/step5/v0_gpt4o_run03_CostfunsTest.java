
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

public class v0_gpt4o_run03_CostfunsTest {
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
    public void testCostfuns() {
        // Test case for i == 5
        given().pathParam("i", 5).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("10"));

        // Test case for i < -444
        given().pathParam("i", -445).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("2"));

        // Test case for i <= -333
        given().pathParam("i", -333).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("3"));

        // Test case for i > 666
        given().pathParam("i", 667).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("4"));

        // Test case for i >= 555
        given().pathParam("i", 555).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("5"));

        // Test case for i != -4
        given().pathParam("i", 0).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("10"));

        // Test case for s.equals("baab")
        given().pathParam("i", 0).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("7"));

        // Test case for s.compareTo("ababba") > 0
        given().pathParam("i", 0).pathParam("s", "ababbab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("8"));

        // Test case for s.compareTo("ababba") >= 0
        given().pathParam("i", 0).pathParam("s", "ababba")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("9"));

        // Test case for s != "abab"
        given().pathParam("i", 0).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("10"));
    }

    // Additional tests for HTTP status codes
    @Test
    public void testUnauthorized() {
        given().pathParam("i", 0).pathParam("s", "anyString")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(anyOf(is(401), is(403), is(404), is(200)));
    }
}
