
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

public class v0_gpt4turbo_run03_CostfunsTest {
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
    public void testCostfunsEndpointValue1() {
        int i = 5;
        String s = "baab";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue2() {
        int i = -445;
        String s = "baab";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue3() {
        int i = 667;
        String s = "baab";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue4() {
        int i = 555;
        String s = "aabb";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue5() {
        int i = -4;
        String s = "aabb";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue6() {
        int i = 0;
        String s = "aabaabba";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsEndpointValue7() {
        int i = 0;
        String s = "mismatch";
        ValidatableResponse response = given().pathParam("i", i).pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("10"));
    }
}
