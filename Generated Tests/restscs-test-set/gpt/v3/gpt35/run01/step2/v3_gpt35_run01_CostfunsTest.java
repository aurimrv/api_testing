
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

public class v3_gpt35_run01_CostfunsTest {

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
    public void testCostfuns_invalidInput_i5() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/5/test")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCostfuns_invalidInput_i9() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/10/abba")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCostfuns_invalidInput_i11() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/11/ab")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCostfuns_schemaValidation() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/1/test")
            .then()
            .statusCode(200)
            .body(matchesJsonSchema(new File("costfuns_schema.json")));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i0() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/5/test")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i1() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/-500/test")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i2() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/-333/test")
            .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i3() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/700/test")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i4() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/555/test")
            .then()
            .statusCode(200)
            .body(equalTo("5"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i5() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/-4/test")
            .then()
            .statusCode(200)
            .body(equalTo("6"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i6() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/0/baab")
            .then()
            .statusCode(200)
            .body(equalTo("7"));
    }

    @Test
    public void testCostfuns_businessRuleEnforcement_i10() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/0/test")
            .then()
            .statusCode(200)
            .body(equalTo("9"));
    }
}
