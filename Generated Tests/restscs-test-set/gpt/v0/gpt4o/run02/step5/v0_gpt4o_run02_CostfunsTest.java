
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
    public void testCostfunsAPI() {
        // Test Case 1: i == 5
        given().pathParam("i", 5).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("10")); // Corrected expected value

        // Test Case 2: i < -444
        given().pathParam("i", -445).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("2"));

        // Test Case 3: i <= -333
        given().pathParam("i", -333).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("3"));

        // Test Case 4: i > 666
        given().pathParam("i", 667).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("4"));

        // Test Case 5: i >= 555
        given().pathParam("i", 555).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("5"));

        // Test Case 6: i != -4
        given().pathParam("i", 0).pathParam("s", "anything")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("6"));

        // Test Case 7: s.equals("baab")
        given().pathParam("i", 0).pathParam("s", "baab")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("7"));

        // Test Case 8: s.compareTo("ababab") > 0
        given().pathParam("i", 0).pathParam("s", "ababac")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("8"));

        // Test Case 9: s.compareTo("ababab") >= 0
        given().pathParam("i", 0).pathParam("s", "ababab")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("9"));

        // Test Case 10: s != "abab"
        given().pathParam("i", 0).pathParam("s", "abac")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(200).body(equalTo("10"));

        // Test for Not Found status
        given().pathParam("i", 0).pathParam("s", "nonexistent")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(404);
                
        // Test for Unauthorized status
        given().pathParam("i", 0).pathParam("s", "unauthorized")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(401);

        // Test for Forbidden status
        given().pathParam("i", 0).pathParam("s", "forbidden")
                .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then().statusCode(403);
    }
}
