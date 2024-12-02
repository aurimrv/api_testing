
package org.restncs;

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

public class v0_gpt4turbo_run02_FisherTest {

    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
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
    public void testFisherEndpoint_m1_n1_x0() {
        given().when().get(baseUrlOfSut + "/api/fisher/1/1/0.0")
            .then().statusCode(200)
            .body("resultAsDouble", is(closeTo(0.0, 0.001)));
    }

    @Test
    public void testFisherEndpoint_m2_n2_x05() {
        given().when().get(baseUrlOfSut + "/api/fisher/2/2/0.5")
            .then().statusCode(200)
            .body("resultAsDouble", is(closeTo(0.333, 0.001)));
    }

    @Test
    public void testFisherEndpoint_m5_n3_x1() {
        given().when().get(baseUrlOfSut + "/api/fisher/5/3/1.0")
            .then().statusCode(200)
            .body("resultAsDouble", is(closeTo(0.465, 0.001)));
    }

    @Test
    public void testFisherEndpoint_m0_n0_x0() {
        given().when().get(baseUrlOfSut + "/api/fisher/0/0/0.0")
            .then().statusCode(200)
            .body("resultAsDouble", is(Double.NaN));
    }

    @Test
    public void testFisherEndpoint_mNegative_nNegative_xNegative() {
        given().when().get(baseUrlOfSut + "/api/fisher/-1/-1/-1.0")
            .then().statusCode(200)
            .body("resultAsDouble", is(Double.NaN));
    }

    @Test
    public void testFisherEndpoint_mLarge_nLarge_xLarge() {
        given().when().get(baseUrlOfSut + "/api/fisher/1000/1000/1000.0")
            .then().statusCode(200)
            .body("resultAsDouble", is(closeTo(1.0, 0.001)));
    }
}
