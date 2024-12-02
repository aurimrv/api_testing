
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

public class v2_gpt4o_run02_NotyPevarTest {

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
    public void testNotyPevarWithValidInputs() {
        given()
            .pathParam("i", 7)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testNotyPevarWithInvalidInputTypes() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testNotyPevarWithEmptyString() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse res = given()
            .pathParam("i", 7)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200);
        
        res.assertThat().body(matchesJsonSchemaInClasspath("notypevar-schema.json"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Asserting business rules, assuming POST is a valid operation for this API
        String resource = given()
            .pathParam("i", 7)
            .pathParam("s", "test")
        .when()
            .post(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(201)
            .extract().body().asString();
        
        given()
            .pathParam("resourceId", resource)
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{resourceId}")
        .then()
            .statusCode(200)
            .body("i", equalTo(7))
            .body("s", equalTo("test"));
        
        // Update resource
        given()
            .pathParam("resourceId", resource)
            .body("{\"i\":8, \"s\":\"updated\"}")
        .when()
            .put(baseUrlOfSut + "/api/notypevar/{resourceId}")
        .then()
            .statusCode(200)
            .body("i", equalTo(8))
            .body("s", equalTo("updated"));
        
        // Delete resource
        given()
            .pathParam("resourceId", resource)
        .when()
            .delete(baseUrlOfSut + "/api/notypevar/{resourceId}")
        .then()
            .statusCode(204);
    }
}
