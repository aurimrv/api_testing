
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

public class v2_gpt4o_run02_RegexTest {

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
        given().pathParam("op", "add").pathParam("arg1", 1).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("3"));
    }

    @Test
    public void testCalcEndpointWithInvalidOperation() {
        given().pathParam("op", "invalidOp").pathParam("arg1", 1).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().assertThat().statusCode(500);
    }

    @Test
    public void testRegexEndpointForUrl() {
        given().pathParam("txt", "http://example.com")
            .when().get(baseUrlOfSut + "/api/pat/{txt}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("url"));
    }

    @Test
    public void testRegexEndpointForDate() {
        given().pathParam("txt", "mon12mar")
            .when().get(baseUrlOfSut + "/api/pat/{txt}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("date"));
    }

    @Test
    public void testRegexEndpointForFpe() {
        given().pathParam("txt", "12.34e+56")
            .when().get(baseUrlOfSut + "/api/pat/{txt}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("fpe"));
    }

    @Test
    public void testRegexEndpointForNone() {
        given().pathParam("txt", "randomtext")
            .when().get(baseUrlOfSut + "/api/pat/{txt}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("none"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().pathParam("dayname", "mon").pathParam("monthname", "mar")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("mon12mar"));
    }

    @Test
    public void testDateParseEndpointWithInvalidData() {
        given().pathParam("dayname", "invalidDay").pathParam("monthname", "invalidMonth")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().assertThat().statusCode(500);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().pathParam("directory", "dir").pathParam("file", "file.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().assertThat().statusCode(200)
            .and().body(equalTo("file.txt"));
    }

    @Test
    public void testFileSuffixEndpointWithInvalidData() {
        given().pathParam("directory", "dir").pathParam("file", "invalidFile")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().assertThat().statusCode(500);
    }

    @Test
    public void testBusinessRuleForPOST() {
        // Implement a POST request test that follows business rules
    }

    @Test
    public void testBusinessRuleForPUT() {
        // Implement a PUT request test that follows business rules
    }

    @Test
    public void testBusinessRuleForDELETE() {
        // Implement a DELETE request test that follows business rules
    }
}
