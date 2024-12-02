
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

public class run08_PatTest {
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
    public void testPatEndpoint() {
        // Test with txt and pat where pat appears in txt
        given().pathParam("txt", "exampletext").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("1"));

        // Test with txt and pat where reverse of pat appears in txt
        given().pathParam("txt", "elpmaxetext").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("2"));

        // Test with txt and pat where both pat and its reverse appear in txt
        given().pathParam("txt", "exampletextelpmaxe").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("3"));

        // Test with txt and pat where a palindrome consisting of pat followed by reverse pat appears in txt
        given().pathParam("txt", "textelpmaxet").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("4"));

        // Test with txt and pat where a palindrome consisting of reverse pat followed by pat appears in txt
        given().pathParam("txt", "elpmaxettext").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("5"));

        // Test with txt and pat where neither pat nor its reverse appears in txt
        given().pathParam("txt", "randomstring").pathParam("pat", "text")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200).body(equalTo("0"));

        // Test response with invalid txt and pat parameters (not found scenario)
        given().pathParam("txt", "nonexistent").pathParam("pat", "none")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(404);

        // Test unauthorized access scenario
        // Assuming unauthorized access is simulated by providing specific parameters (in reality, handled by security mechanisms)
        given().pathParam("txt", "unauthorized").pathParam("pat", "access")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(401);
        
        // Test forbidden access scenario
        given().pathParam("txt", "forbidden").pathParam("pat", "access")
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(403);
    }
}
