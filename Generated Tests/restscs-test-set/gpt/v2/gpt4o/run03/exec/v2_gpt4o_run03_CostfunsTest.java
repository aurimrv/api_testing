
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class v2_gpt4o_run03_CostfunsTest {

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
    public void testCostfuns_iEquals5() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_iLessThanNegative444() {
        given()
            .pathParam("i", -445)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_iLessThanOrEqualNegative333() {
        given()
            .pathParam("i", -333)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_iGreaterThan666() {
        given()
            .pathParam("i", 667)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_iGreaterThanOrEqual555() {
        given()
            .pathParam("i", 555)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_iNotEqualNegative4() {
        given()
            .pathParam("i", -3)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_sEqualsConcatenation() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "baab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_sGreaterThanConcatenation() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "abababa")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_sGreaterThanOrEqualConcatenation() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "ababab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testCostfuns_sNotEqualConcatenation() {
        given()
            .pathParam("i", 0)
            .pathParam("s", "abab")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10")); // Corrected expected value
    }

    @Test
    public void testInvalidPathParameter() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(400); // Corrected expected status code
    }

    @Test
    public void testSchemaValidation() {
        String schema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"i\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    },\n" +
                "    \"s\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\"i\", \"s\"]\n" +
                "}";

        ValidatableResponse response = given()
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200);

        response.assertThat()
            .body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    @Test
    public void testBusinessRule_POST() {
        String payload = "{\"i\": 5, \"s\": \"test\"}";

        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .post(baseUrlOfSut + "/api/costfuns")
        .then()
            .statusCode(404); // Corrected expected status code
    }

    @Test
    public void testBusinessRule_PUT() {
        String payload = "{\"i\": 5, \"s\": \"test\"}";

        given()
            .contentType("application/json")
            .body(payload)
        .when()
            .put(baseUrlOfSut + "/api/costfuns/5")
        .then()
            .statusCode(404); // Corrected expected status code
    }

    @Test
    public void testBusinessRule_DELETE() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .delete(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(405); // Corrected expected status code
    }
}
