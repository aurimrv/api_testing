
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

public class v3_gpt4o_run03_NotyPevarTest {

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
    public void testNotyPevarUsingGET() {
        ValidatableResponse response = given()
            .pathParam("i", 1)
            .pathParam("s", "hello")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
        
        response = given()
            .pathParam("i", 7)
            .pathParam("s", "a")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
        
        response = given()
            .pathParam("i", 7)
            .pathParam("s", "zzz")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
        
        response = given()
            .pathParam("i", 10)
            .pathParam("s", "a")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("3"));

        // Simulate invalid input to force 500 Internal Server Error
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "a")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "hello")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("notypevar-response-schema.json"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // POST, PUT, DELETE operations not provided in the swagger, assuming basic tests for GET
        given()
            .pathParam("i", 1)
            .pathParam("s", "hello")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testEndpointResponses() {
        // Testing all possible responses as per Swagger documentation
        given()
            .pathParam("i", 56)
            .pathParam("s", "hello")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("56"));
        
        given()
            .pathParam("i", 1)
            .pathParam("s", "world")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
        
        given()
            .pathParam("i", 100)
            .pathParam("s", "aaa")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(404);
    }
}
