
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

public class v0_gpt4o_run01_DateParseTest {

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
    public void testDateParse() {
        // Test valid day and month
        given().get(baseUrlOfSut + "/api/dateparse/mon/jan")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().get(baseUrlOfSut + "/api/dateparse/tue/feb")
            .then().statusCode(200)
            .body(equalTo("3"));

        given().get(baseUrlOfSut + "/api/dateparse/wed/mar")
            .then().statusCode(200)
            .body(equalTo("4"));

        given().get(baseUrlOfSut + "/api/dateparse/thur/apr")
            .then().statusCode(200)
            .body(equalTo("5"));

        given().get(baseUrlOfSut + "/api/dateparse/fri/may")
            .then().statusCode(200)
            .body(equalTo("6"));

        given().get(baseUrlOfSut + "/api/dateparse/sat/jun")
            .then().statusCode(200)
            .body(equalTo("7"));

        given().get(baseUrlOfSut + "/api/dateparse/sun/jul")
            .then().statusCode(200)
            .body(equalTo("8"));

        // Test invalid day and valid month
        given().get(baseUrlOfSut + "/api/dateparse/invalid/jan")
            .then().statusCode(200)
            .body(equalTo("1"));

        // Test valid day and invalid month
        given().get(baseUrlOfSut + "/api/dateparse/mon/invalid")
            .then().statusCode(200)
            .body(equalTo("1"));

        // Test invalid day and month
        given().get(baseUrlOfSut + "/api/dateparse/invalid/invalid")
            .then().statusCode(200)
            .body(equalTo("0"));

        // Test empty day and month
        given().get(baseUrlOfSut + "/api/dateparse//")
            .then().statusCode(404); // No path parameter provided

        // Test unauthorized and forbidden responses
        // Assuming the application supports these status codes based on the swagger documentation
        // The actual implementation for generating these statuses will depend on the app's auth mechanism
        given().auth().basic("invalidUser", "invalidPass")
            .get(baseUrlOfSut + "/api/dateparse/mon/jan")
            .then().statusCode(401);

        given().auth().basic("forbiddenUser", "forbiddenPass")
            .get(baseUrlOfSut + "/api/dateparse/mon/jan")
            .then().statusCode(403);
    }
}
