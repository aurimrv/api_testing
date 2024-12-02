
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

public class v0_gpt4o_run02_CostfunsTest {

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
    public void testCostfunsUsingGET() {
        // Test case for different values of 'i' and 's' to maximize decision coverage
        ValidatableResponse response;

        // i == 5
        response = given().get(baseUrlOfSut + "/api/costfuns/5/anyString").then();
        response.statusCode(200).body(equalTo("1"));

        // i < -444
        response = given().get(baseUrlOfSut + "/api/costfuns/-445/anyString").then();
        response.statusCode(200).body(equalTo("2"));

        // i <= -333
        response = given().get(baseUrlOfSut + "/api/costfuns/-333/anyString").then();
        response.statusCode(200).body(equalTo("3"));

        // i > 666
        response = given().get(baseUrlOfSut + "/api/costfuns/667/anyString").then();
        response.statusCode(200).body(equalTo("4"));

        // i >= 555
        response = given().get(baseUrlOfSut + "/api/costfuns/555/anyString").then();
        response.statusCode(200).body(equalTo("5"));

        // i != -4
        response = given().get(baseUrlOfSut + "/api/costfuns/1/anyString").then();
        response.statusCode(200).body(equalTo("6"));

        // s.equals("baab")
        response = given().get(baseUrlOfSut + "/api/costfuns/0/baab").then();
        response.statusCode(200).body(equalTo("7"));

        // s.compareTo("ababab") > 0
        response = given().get(baseUrlOfSut + "/api/costfuns/0/abac").then();
        response.statusCode(200).body(equalTo("8"));

        // s.compareTo("ababab") >= 0
        response = given().get(baseUrlOfSut + "/api/costfuns/0/ababab").then();
        response.statusCode(200).body(equalTo("9"));

        // s != "abab"
        response = given().get(baseUrlOfSut + "/api/costfuns/0/abcd").then();
        response.statusCode(200).body(equalTo("10"));
    }
}
