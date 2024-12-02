
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

public class v2_gpt35_run03_FisherTest {
    
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
    public void testInvalidInputsForFisher() {
        int m = 1;
        int n = 0;
        double x = 2.5;

        given()
            .when()
            .get(baseUrlOfSut + "/api/fisher/" + m + "/" + n + "/" + x)
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForFisher() {
        int m = 10;
        int n = 5;
        double x = 1.5;

        ValidatableResponse response = given()
            .when()
            .get(baseUrlOfSut + "/api/fisher/" + m + "/" + n + "/" + x)
            .then()
            .statusCode(200);

        // Validate schema conformity here
    }

    @Test
    public void testBusinessRuleForFisher() {
        int m = 4;
        int n = 6;
        double x = 2.0;

        given()
            .when()
            .get(baseUrlOfSut + "/api/fisher/" + m + "/" + n + "/" + x)
            .then()
            .statusCode(200);
        
        // Add business rule validation assertions here
    }
}

