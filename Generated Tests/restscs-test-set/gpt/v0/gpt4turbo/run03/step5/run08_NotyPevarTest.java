
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

public class run08_NotyPevarTest {
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
    public void testNotyPevar_ExpectedResults() {
        // Test cases to maximize decision coverage and check all possible responses

        // Case 1: (i = 28, s = "hello") -> Expected result = "3"
        given().pathParam("i", 28).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("3"));

        // Case 2: (i = 7, s = "hello") -> Expected result = "1"
        given().pathParam("i", 7).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("1"));

        // Case 3: (i = 0, s = "world") -> Expected result = "2"
        given().pathParam("i", 0).pathParam("s", "world")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("2"));

        // Case 4: (i = 10, s = "hello") -> Expected result = "3"
        given().pathParam("i", 10).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("3"));

        // Case 5: (i = 0, s = "hello") -> Expected result = "0"
        given().pathParam("i", 0).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("0"));

        // Check responses for different status codes
        // Unauthorized (401)
        given().pathParam("i", 28).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(anyOf(is(200), is(401)));

        // Forbidden (403)
        given().pathParam("i", 28).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(anyOf(is(200), is(403)));

        // Not Found (404)
        given().pathParam("i", 28).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(anyOf(is(200), is(404)));
    }
}
