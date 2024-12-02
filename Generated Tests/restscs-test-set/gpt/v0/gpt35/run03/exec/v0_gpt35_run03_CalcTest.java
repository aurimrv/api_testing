
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

public class v0_gpt35_run03_CalcTest {

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
    public void testCalcOperations() {
        // Test constant operators
        ValidatableResponse response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/pi/0/0")
                .then()
                .statusCode(200);
        
        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/e/0/0")
                .then()
                .statusCode(200);

        // Test unary operators
        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/sqrt/4/0")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/log/10/0")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/sine/0/0")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/cosine/0/0")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/tangent/0/0")
                .then()
                .statusCode(200);

        // Test binary operators
        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/plus/2/3")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/subtract/5/3")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/multiply/2/3")
                .then()
                .statusCode(200);

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/divide/6/3")
                .then()
                .statusCode(200);
    }
}
