
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
import io.restassured.path.json.JsonPath;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.util.Arrays;

public class v3_gpt4o_run01_FileSuffixTest {

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
    public void testFileSuffix1() {
        given().pathParam("directory", "text")
                .pathParam("file", "example.txt")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("1"));
    }

    @Test
    public void testFileSuffix2() {
        given().pathParam("directory", "acrobat")
                .pathParam("file", "example.pdf")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("2"));
    }

    @Test
    public void testFileSuffix3() {
        given().pathParam("directory", "word")
                .pathParam("file", "example.doc")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("3"));
    }

    @Test
    public void testFileSuffix4() {
        given().pathParam("directory", "bin")
                .pathParam("file", "example.exe")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("4"));
    }

    @Test
    public void testFileSuffix5() {
        given().pathParam("directory", "lib")
                .pathParam("file", "example.dll")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("5"));
    }

    @Test
    public void testFileSuffixInvalidDirectory() {
        given().pathParam("directory", "unknown")
                .pathParam("file", "example.txt")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixInvalidFile() {
        given().pathParam("directory", "text")
                .pathParam("file", "example.unknown")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200)
                .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixInternalServerError() {
        given().pathParam("directory", "text")
                .pathParam("file", "")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().pathParam("directory", "text")
                .pathParam("file", "example.txt")
                .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(200);
        response.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("fileSuffixSchema.json"));
    }

    @Test
    public void testBusinessRuleEnforcementPost() {
        given().contentType("application/json")
                .body("{\"directory\":\"text\",\"file\":\"example.txt\"}")
                .when().post(baseUrlOfSut + "/api/filesuffix")
                .then().statusCode(405); // Assuming POST is not allowed
    }

    @Test
    public void testBusinessRuleEnforcementPut() {
        given().contentType("application/json")
                .body("{\"directory\":\"text\",\"file\":\"example.txt\"}")
                .when().put(baseUrlOfSut + "/api/filesuffix")
                .then().statusCode(405); // Assuming PUT is not allowed
    }

    @Test
    public void testBusinessRuleEnforcementDelete() {
        given().pathParam("directory", "text")
                .pathParam("file", "example.txt")
                .when().delete(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
                .then().statusCode(405); // Assuming DELETE is not allowed
    }
}
