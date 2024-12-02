
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

public class v3_gpt4turbo_run03_NotyPevarTest {

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
    public void testSubjectMethodWithCorrectValues() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 28)
            .pathParam("s", "hello")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testSubjectMethodWithBoundaryValues() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 14)
            .pathParam("s", "hello")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testSubjectMethodWithInvalidInputs() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", -1)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200);
    }

    @Test
    public void testSubjectMethodWithServerErrorSimulation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", Integer.MAX_VALUE)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200);
    }

    @Test
    public void testSubjectMethodWithComparisonLessThanZero() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 0)
            .pathParam("s", "world")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("2"));
    }
}
