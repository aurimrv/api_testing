
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

public class v3_gpt4turbo_run01_CostfunsTest {

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
    public void testSubjectWithIEquals5() {
        given().pathParam("i", 5).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testSubjectWithILessThanMinus444() {
        given().pathParam("i", -445).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testSubjectWithIGreaterThan666() {
        given().pathParam("i", 667).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testSubjectWithIEqualsMinus4() {
        given().pathParam("i", -4).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("0"));  // Assuming the default case is returned '0' when all conditions fail.
    }

    @Test
    public void testSubjectWithInvalidInput() {
        given().pathParam("i", "invalid").pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(500);
    }

    @Test
    public void testSubjectWithBoundaryValue() {
        given().pathParam("i", Integer.MAX_VALUE).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(anyOf(equalTo("4"), equalTo("5")));
    }

    @Test
    public void testSubjectWithSpecialCharactersInString() {
        given().pathParam("i", 5).pathParam("s", "ba@#$$%^&*()ab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("1"));
    }
}
