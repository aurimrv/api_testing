
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

public class v2_gpt4o_run03_NotyPevarTest {

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
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
            .when().get("/api/calc/{op}/{arg1}/{arg2}").then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("calcResponseSchema.json"));
        
        response.body(equalTo("8.0"));
    }

    @Test
    public void testCalcEndpointInvalidOp() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("op", "invalid")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
            .when().get("/api/calc/{op}/{arg1}/{arg2}").then()
            .statusCode(500);
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
            .when().get("/api/cookie/{name}/{val}/{site}").then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("cookieResponseSchema.json"));
        
        response.body(equalTo("Cookie set"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "hello")
            .when().get("/api/notypevar/{i}/{s}").then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("notypevarResponseSchema.json"));
        
        response.body(equalTo("3"));
    }

    @Test
    public void testNotyPevarEndpointInvalid() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", "invalid")
            .pathParam("s", "hello")
            .when().get("/api/notypevar/{i}/{s}").then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForNotyPevar() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}").then()
            .statusCode(200)
            .body("result", is(notNullValue()));
    }

    @Test
    public void testBusinessRuleEnforcementPost() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"name\": \"test\", \"value\": \"123\"}")
            .when().post("/api/resource").then()
            .statusCode(201)
            .body("id", is(notNullValue()));
    }

    @Test
    public void testBusinessRuleEnforcementPut() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"name\": \"testUpdate\", \"value\": \"456\"}")
            .when().put("/api/resource/1").then()
            .statusCode(200)
            .body("message", equalTo("Resource updated"));
    }

    @Test
    public void testBusinessRuleEnforcementDelete() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().delete("/api/resource/1").then()
            .statusCode(204);
    }
}
