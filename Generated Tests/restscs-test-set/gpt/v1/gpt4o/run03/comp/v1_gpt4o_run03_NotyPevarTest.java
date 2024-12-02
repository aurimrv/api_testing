
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

public class v1_gpt4o_run03_NotyPevarTest {
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
    public void testCalcEndpoint() {
        given().pathParam("op", "add")
               .pathParam("arg1", 2.0)
               .pathParam("arg2", 3.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(is("5.0"));

        given().pathParam("op", "sub")
               .pathParam("arg1", 5.0)
               .pathParam("arg2", 3.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(is("2.0"));

        given().pathParam("op", "mul")
               .pathParam("arg1", 4.0)
               .pathParam("arg2", 2.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(is("8.0"));

        given().pathParam("op", "div")
               .pathParam("arg1", 8.0)
               .pathParam("arg2", 2.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(is("4.0"));

        given().pathParam("op", "mod")
               .pathParam("arg1", 9.0)
               .pathParam("arg2", 4.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(is("1.0"));

        given().pathParam("op", "invalid")
               .pathParam("arg1", 1.0)
               .pathParam("arg2", 1.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given().pathParam("name", "session")
               .pathParam("val", "12345")
               .pathParam("site", "example.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200).body(notNullValue());

        given().pathParam("name", "invalid")
               .pathParam("val", "nothing")
               .pathParam("site", "example.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(404);
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().pathParam("i", 56)
               .pathParam("s", "world")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(is("56"));

        given().pathParam("i", 7)
               .pathParam("s", "world")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(is("1"));

        given().pathParam("i", 5)
               .pathParam("s", "hello")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(is("3"));

        given().pathParam("i", 3)
               .pathParam("s", "a")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(is("2"));

        given().pathParam("i", 1)
               .pathParam("s", "b")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(is("0"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().pathParam("i", 56)
               .pathParam("s", "world")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(is("56"));

        given().pathParam("i", 7)
               .pathParam("s", "world")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(is("1"));

        given().pathParam("i", 5)
               .pathParam("s", "hello")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(is("3"));

        given().pathParam("i", 3)
               .pathParam("s", "a")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(is("2"));

        given().pathParam("i", 1)
               .pathParam("s", "b")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(is("0"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().pathParam("dayname", "Monday")
               .pathParam("monthname", "January")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200).body(notNullValue());

        given().pathParam("dayname", "InvalidDay")
               .pathParam("monthname", "InvalidMonth")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(404);
    }

    // Additional tests for other endpoints can be added here following the same pattern
}
