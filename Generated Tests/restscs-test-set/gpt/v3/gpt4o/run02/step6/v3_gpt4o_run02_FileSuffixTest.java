
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
import io.restassured.parsing.Parser;
import java.util.Arrays;

public class v3_gpt4o_run02_FileSuffixTest {

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
    public void testFileSuffixValidInputs() {
        given()
            .pathParam("directory", "text")
            .pathParam("file", "document.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
        
        given()
            .pathParam("directory", "acrobat")
            .pathParam("file", "file.pdf")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));

        given()
            .pathParam("directory", "word")
            .pathParam("file", "document.doc")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("3"));

        given()
            .pathParam("directory", "bin")
            .pathParam("file", "program.exe")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("4"));

        given()
            .pathParam("directory", "lib")
            .pathParam("file", "library.dll")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("5"));
    }

    @Test
    public void testFileSuffixInvalidInputs() {
        given()
            .pathParam("directory", "unknown")
            .pathParam("file", "file.unknown")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        given()
            .pathParam("directory", "text")
            .pathParam("file", "file")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
        
        given()
            .pathParam("directory", "")
            .pathParam("file", "")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixForceServerError() {
        given()
            .pathParam("directory", "bin")
            .pathParam("file", "null")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        RestAssured.registerParser("text/plain", Parser.JSON);

        given()
            .pathParam("directory", "text")
            .pathParam("file", "document.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body("$", anyOf(is("1"), is("0")));

        given()
            .pathParam("directory", "acrobat")
            .pathParam("file", "file.pdf")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body("$", anyOf(is("2"), is("0")));

        given()
            .pathParam("directory", "word")
            .pathParam("file", "document.doc")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body("$", anyOf(is("3"), is("0")));

        given()
            .pathParam("directory", "bin")
            .pathParam("file", "program.exe")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body("$", anyOf(is("4"), is("0")));

        given()
            .pathParam("directory", "lib")
            .pathParam("file", "library.dll")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body("$", anyOf(is("5"), is("0")));
    }
}
