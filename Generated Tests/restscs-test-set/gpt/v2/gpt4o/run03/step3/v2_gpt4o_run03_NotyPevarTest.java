
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
    public void testInvalidPathParamReturns500() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForNotyPevar() {
        String expectedSchema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"result\": { \"type\": \"string\" }\n" +
                "  },\n" +
                "  \"required\": [\"result\"]\n" +
                "}";
        
        given()
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(expectedSchema));
    }

    @Test
    public void testBusinessRuleForNotyPevar() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testInvalidOperationReturns500() {
        given()
            .pathParam("op", "invalidop")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForCalc() {
        String expectedSchema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"result\": { \"type\": \"number\" }\n" +
                "  },\n" +
                "  \"required\": [\"result\"]\n" +
                "}";
        
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(expectedSchema));
    }

    @Test
    public void testBusinessRuleForCalc() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testInvalidDirectoryReturns500() {
        given()
            .pathParam("directory", "invalid")
            .pathParam("file", "test.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForFileSuffix() {
        String expectedSchema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"suffix\": { \"type\": \"string\" }\n" +
                "  },\n" +
                "  \"required\": [\"suffix\"]\n" +
                "}";
        
        given()
            .pathParam("directory", "dir")
            .pathParam("file", "test.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(expectedSchema));
    }

    @Test
    public void testBusinessRuleForFileSuffix() {
        given()
            .pathParam("directory", "dir")
            .pathParam("file", "test.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testInvalidDayNameReturns500() {
        given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForDateParse() {
        String expectedSchema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"date\": { \"type\": \"string\" }\n" +
                "  },\n" +
                "  \"required\": [\"date\"]\n" +
                "}";
        
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(expectedSchema));
    }

    @Test
    public void testBusinessRuleForDateParse() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("01-01")); // Assuming the business rule
    }

    @Test
    public void testInvalidTitleReturns500() {
        given()
            .pathParam("sex", "invalid")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidationForTitle() {
        String expectedSchema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"title\": { \"type\": \"string\" }\n" +
                "  },\n" +
                "  \"required\": [\"title\"]\n" +
                "}";
        
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(expectedSchema));
    }

    @Test
    public void testBusinessRuleForTitle() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr. Male")); // Assuming the business rule
    }
}
