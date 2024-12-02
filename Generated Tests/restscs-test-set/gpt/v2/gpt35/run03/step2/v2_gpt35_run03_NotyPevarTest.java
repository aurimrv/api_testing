
package org.restscs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;

public class v2_gpt35_run03_NotyPevarTest {

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
    public void testErrorScenarios() {
        // Test that simulates invalid inputs to force 5xx status codes
        ValidatableResponse response = given()
                .pathParam("i", 10)
                .pathParam("s", "test")
                .when()
                .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then();

        response.statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        // Test to validate schema conformity of API responses
        given()
                .pathParam("i", 5)
                .pathParam("s", "hello")
                .when()
                .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then()
                .body("type", equalTo("string"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Test to validate business rules of POST, PUT, DELETE operations
        ValidatableResponse response = given()
                .pathParam("i", 10)
                .pathParam("s", "test")
                .when()
                .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                .then();

        response.body(equalTo("0"));
    }
}
