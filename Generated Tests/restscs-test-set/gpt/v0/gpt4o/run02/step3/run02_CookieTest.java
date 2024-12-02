
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

public class v0_gpt4o_run02_CookieTest {
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
    public void testCookieEndpoint() {
        // Test case for name = "userid"
        given().pathParam("name", "userid").pathParam("val", "user1234").pathParam("site", "example.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("1"));

        // Test case for name = "session", val = "am", site = "abc.com"
        given().pathParam("name", "session").pathParam("val", "am").pathParam("site", "abc.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("1"));

        // Test case for name = "session", val != "am", site = "abc.com"
        given().pathParam("name", "session").pathParam("val", "pm").pathParam("site", "abc.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("2"));

        // Test case for name = "session", val = "am", site != "abc.com"
        given().pathParam("name", "session").pathParam("val", "am").pathParam("site", "example.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("2"));

        // Test case for name != "userid" and name != "session"
        given().pathParam("name", "other").pathParam("val", "value").pathParam("site", "example.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200).body(equalTo("0"));

        // Test cases to cover all possible status codes
        given().pathParam("name", "userid").pathParam("val", "user1234").pathParam("site", "example.com")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }
}
