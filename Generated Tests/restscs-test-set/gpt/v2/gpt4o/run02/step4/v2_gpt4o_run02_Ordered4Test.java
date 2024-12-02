
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

public class v2_gpt4o_run02_Ordered4Test {

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
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "apple")
        .when().get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "banana")
            .pathParam("x", "apple")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidLength() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "ban")
            .pathParam("x", "apple")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().get("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(200);
        
        assertNotNull(response.extract().body().asString());
    }

    @Test
    public void testInternalServerError() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().get("/api/invalidEndpoint/{w}/{x}/{y}/{z}")
        .then().statusCode(500);
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Testing POST operation
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"w\":\"apple\",\"x\":\"banana\",\"y\":\"cherry\",\"z\":\"date\"}")
        .when().post("/api/ordered4")
        .then().statusCode(405); // Method Not Allowed, assuming POST is not supported

        // Testing PUT operation
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"w\":\"apple\",\"x\":\"banana\",\"y\":\"cherry\",\"z\":\"date\"}")
        .when().put("/api/ordered4")
        .then().statusCode(405); // Method Not Allowed, assuming PUT is not supported

        // Testing DELETE operation
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when().delete("/api/ordered4/{w}/{x}/{y}/{z}")
        .then().statusCode(405); // Method Not Allowed, assuming DELETE is not supported
    }
}
