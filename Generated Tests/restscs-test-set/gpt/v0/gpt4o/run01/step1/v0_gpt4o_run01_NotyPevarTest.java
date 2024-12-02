
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

public class v0_gpt4o_run01_NotyPevarTest {

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
    public void testNotyPevarUsingGET_200() {
        // Test with various values to cover different branches
        given().pathParam("i", 28).pathParam("s", "abc")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("0"));

        given().pathParam("i", 7).pathParam("s", "hello")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("1"));

        given().pathParam("i", 10).pathParam("s", "z")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("2"));

        given().pathParam("i", 10).pathParam("s", "abc")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200).body(equalTo("3"));
    }

    @Test
    public void testNotyPevarUsingGET_401() {
        // Assuming we need an authentication token and it is missing
        given().pathParam("i", 28).pathParam("s", "abc")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(401);
    }

    @Test
    public void testNotyPevarUsingGET_403() {
        // Assuming some forbidden access scenario
        given().pathParam("i", 28).pathParam("s", "abc")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(403);
    }

    @Test
    public void testNotyPevarUsingGET_404() {
        // Invalid endpoint
        given().pathParam("i", 28).pathParam("s", "abc")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}invalid")
            .then().statusCode(404);
    }
}
