
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

public class v1_gpt35_run02_TriangleClassificationTest {

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
    public void testTriangleClassification() {
        int a = 3;
        int b = 4;
        int c = 5;
        
        int expected = TriangleClassification.classify(a, b, c);
        
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/triangle/" + a + "/" + b + "/" + c)
                .when().get()
                .then();
        
        response.statusCode(200);
        response.body("resultAsInt", equalTo(expected));
    }

    @Test
    public void testTriangleClassificationEquilateral() {
        int a = 2;
        int b = 2;
        int c = 2;
        
        int expected = TriangleClassification.classify(a, b, c);
        
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/triangle/" + a + "/" + b + "/" + c)
                .when().get()
                .then();
        
        response.statusCode(200);
        response.body("resultAsInt", equalTo(expected));
    }

    @Test
    public void testTriangleClassificationIsosceles() {
        int a = 3;
        int b = 3;
        int c = 4;
        
        int expected = TriangleClassification.classify(a, b, c);
        
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/triangle/" + a + "/" + b + "/" + c)
                .when().get()
                .then();
        
        response.statusCode(200);
        response.body("resultAsInt", equalTo(expected));
    }

    @Test
    public void testTriangleClassificationScalene() {
        int a = 3;
        int b = 4;
        int c = 6;
        
        int expected = TriangleClassification.classify(a, b, c);
        
        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/triangle/" + a + "/" + b + "/" + c)
                .when().get()
                .then();
        
        response.statusCode(200);
        response.body("resultAsInt", equalTo(expected));
    }

}
