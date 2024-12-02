
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

public class v0_gpt4o_run01_PatTest {

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
        // Test case 1: pat found in txt
        given().pathParam("txt", "abcdefgh").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("1"));

        // Test case 2: reverse pat found in txt
        given().pathParam("txt", "abcdefgh").pathParam("pat", "cba")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("2"));

        // Test case 3: both pat and reverse pat found in txt
        given().pathParam("txt", "abczyxcba").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("3"));

        // Test case 4: palindrome pat followed by reverse pat found in txt
        given().pathParam("txt", "abczyxabc").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("4"));

        // Test case 5: palindrome reverse pat followed by pat found in txt
        given().pathParam("txt", "cbazyxabc").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("5"));

        // Test case 6: pat not found in txt
        given().pathParam("txt", "abcdefgh").pathParam("pat", "xyz")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("0"));

        // Test case 7: txt or pat has length less than 3
        given().pathParam("txt", "ab").pathParam("pat", "ab")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("0"));
        
        given().pathParam("txt", "abcd").pathParam("pat", "ab")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("0"));

        // Test case 8: txt is empty string
        given().pathParam("txt", "").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("0"));

        // Test case 9: pat is empty string
        given().pathParam("txt", "abcdefgh").pathParam("pat", "")
                .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
                .then().statusCode(200).body(equalTo("0"));
    }

    @Test
    public void testNotFoundStatus() {
        given().pathParam("txt", "abcdefgh").pathParam("pat", "abc")
                .when().get(baseUrlOfSut + "/api/pat_not_exist/{txt}/{pat}")
                .then().statusCode(404);
    }

    @Test
    public void testUnauthorizedStatus() {
        // Setup necessary to simulate unauthorized access, if applicable
    }

    @Test
    public void testForbiddenStatus() {
        // Setup necessary to simulate forbidden access, if applicable
    }
}
