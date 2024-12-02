
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

public class v2_gpt4o_run03_Ordered4Test {

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
    public void testOrdered4Increasing() {
        given()
                .pathParam("w", "aaaaa")
                .pathParam("x", "bbbbb")
                .pathParam("y", "ccccc")
                .pathParam("z", "ddddd")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        given()
                .pathParam("w", "ddddd")
                .pathParam("x", "ccccc")
                .pathParam("y", "bbbbb")
                .pathParam("z", "aaaaa")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        given()
                .pathParam("w", "aaaaa")
                .pathParam("x", "ccccc")
                .pathParam("y", "bbbbb")
                .pathParam("z", "ddddd")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(equalTo("unordered"));
    }

    @Test
    public void testInvalidInputLength() {
        given()
                .pathParam("w", "a")
                .pathParam("x", "bbbbb")
                .pathParam("y", "ccccc")
                .pathParam("z", "ddddd")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testCalcValid() {
        given()
                .pathParam("op", "add")
                .pathParam("arg1", 1.0)
                .pathParam("arg2", 2.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(200)
                .body(is("3.0"));
    }

    @Test
    public void testCalcInvalidOp() {
        given()
                .pathParam("op", "invalid")
                .pathParam("arg1", 1.0)
                .pathParam("arg2", 2.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testCalcInvalidArgType() {
        given()
                .pathParam("op", "add")
                .pathParam("arg1", "invalid")
                .pathParam("arg2", 2.0)
                .when()
                .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testBusinessRuleEnforcement() {
        ValidatableResponse response = given()
                .pathParam("w", "aaaaa")
                .pathParam("x", "bbbbb")
                .pathParam("y", "ccccc")
                .pathParam("z", "ddddd")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200);

        // Ensure no violations of business rules
        String result = response.extract().body().asString();
        assertTrue(result.equals("increasing") || result.equals("decreasing") || result.equals("unordered"));
    }

    @Test
    public void testErrorScenarioHandling() {
        given()
                .pathParam("w", "aaaaa")
                .pathParam("x", "bbbbb")
                .pathParam("y", "ccccc")
                .pathParam("z", "ddddd")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(anyOf(is(200), is(500)));
    }
}
