
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

public class v2_gpt4turbo_run03_FileSuffixTest {

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
    public void testFileSuffixValidInput() {
        given().baseUri(baseUrlOfSut)
               .pathParam("directory", "word")
               .pathParam("file", "document.doc")
               .when().get("/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo("3")); // Expect result 3 for word/doc combination
    }

    @Test
    public void testFileSuffixInvalidFileInput() {
        given().baseUri(baseUrlOfSut)
               .pathParam("directory", "word")
               .pathParam("file", "invalidfile")
               .when().get("/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo("0")); // Expect result 0 for invalid suffix
    }

    @Test
    public void testFileSuffixInvalidDirectoryInput() {
        given().baseUri(baseUrlOfSut)
               .pathParam("directory", "nonexistent")
               .pathParam("file", "document.doc")
               .when().get("/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo("0")); // Expect result 0 for invalid directory
    }

    @Test
    public void testFileSuffixErrorScenario() {
        given().baseUri(baseUrlOfSut)
               .pathParam("directory", "")
               .pathParam("file", "")
               .when().get("/api/filesuffix/{directory}/{file}")
               .then().statusCode(404); // Expect 404 when parameters are empty
    }

    @Test
    public void testFileSuffixSchemaValidation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
                                              .pathParam("directory", "word")
                                              .pathParam("file", "document.doc")
                                              .when().get("/api/filesuffix/{directory}/{file}")
                                              .then().statusCode(200);
        JsonPath jsonPathEvaluator = response.extract().jsonPath();
        assertTrue(jsonPathEvaluator.get("$") instanceof String); // Check if the response is of type String
    }
}
