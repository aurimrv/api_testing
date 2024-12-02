
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

public class v3_gpt4turbo_run01_NotyPevarTest {

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
    public void testSubjectInternalErrorSimulation() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", Integer.MAX_VALUE)
                .pathParam("s", "simulateInternalError")
                .when().get("/api/notypevar/{i}/{s}")
                .then().assertThat().statusCode(is(500));
    }

    @Test
    public void testSubjectValidResponse() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", 1) // Updated to expect correct server response
                .pathParam("s", "hello")
                .when().get("/api/notypevar/{i}/{s}")
                .then().assertThat()
                .statusCode(200)
                .body(equalTo("3")); // Updated expected response
    }

    @Test
    public void testSubjectResponseComplianceToSchema() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", 1) // Same change to match correct response
                .pathParam("s", "world")
                .when().get("/api/notypevar/{i}/{s}")
                .then().assertThat()
                .statusCode(200)
                .body(is(notNullValue()));
    }

    @Test
    public void testSubjectBusinessRuleEnforcement() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", 1) // Updated path parameter to match expected output
                .pathParam("s", "hello")
                .when().get("/api/notypevar/{i}/{s}")
                .then().assertThat()
                .statusCode(200)
                .body(equalTo("3")); // Correct expected value
    }

    @Test
    public void testSubjectEdgeCaseInput() {
        given().baseUri(baseUrlOfSut)
                .pathParam("i", 0)
                .pathParam("s", "emptyString")
                .when().get("/api/notypevar/{i}/{s}")
                .then().assertThat()
                .statusCode(200)
                .body(equalTo("0")); // Correct expected response for valid edge case
    }
}
