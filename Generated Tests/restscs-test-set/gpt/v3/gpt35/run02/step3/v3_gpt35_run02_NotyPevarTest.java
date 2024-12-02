
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

public class v3_gpt35_run02_NotyPevarTest {

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
    public void testInvalidInputReturns5xxCode() {
        given()
            .pathParam("i", 10)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(not(200))
            .statusCode(greaterThanOrEqualTo(500))
            .log().all();
    }

    @Test
    public void testSchemaValidation() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "example")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .assertThat()
            .body(matchesJsonSchema(new File("schema.json")))
            .log().all();
    }

    @Test
    public void testBusinessRuleEnforcement() {
        given()
            .pathParam("i", 8)
            .pathParam("s", "validation")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("2"))
            .log().all();
    }
}
