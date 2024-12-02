
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

public class v1_gpt4o_run01_CostfunsTest {

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
    public void testCostfunsEndpoint_i5_s_abba() {
        given().pathParam("i", 5)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("1"));
    }

    @Test
    public void testCostfunsEndpoint_i_minus445_s_abba() {
        given().pathParam("i", -445)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("2"));
    }

    @Test
    public void testCostfunsEndpoint_i_minus333_s_abba() {
        given().pathParam("i", -333)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("3"));
    }

    @Test
    public void testCostfunsEndpoint_i667_s_abba() {
        given().pathParam("i", 667)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("4"));
    }

    @Test
    public void testCostfunsEndpoint_i555_s_abba() {
        given().pathParam("i", 555)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("5"));
    }

    @Test
    public void testCostfunsEndpoint_i_minus3_s_abba() {
        given().pathParam("i", -3)
               .pathParam("s", "abba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("6"));
    }

    @Test
    public void testCostfunsEndpoint_i5_s_baab() {
        given().pathParam("i", 5)
               .pathParam("s", "baab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("7"));
    }

    @Test
    public void testCostfunsEndpoint_i5_s_abbaab() {
        given().pathParam("i", 5)
               .pathParam("s", "abbaab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("8"));
    }

    @Test
    public void testCostfunsEndpoint_i5_s_abbaab_with_ab() {
        given().pathParam("i", 5)
               .pathParam("s", "abbaab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("9"));
    }

    @Test
    public void testCostfunsEndpoint_i5_s_abab() {
        given().pathParam("i", 5)
               .pathParam("s", "abab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
                      .body(equalTo("10"));
    }
}
