
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

public class v3_gpt4o_run03_CookieTest {

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
    public void testCalcEndpoint() {
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
    public void testCookieEndpoint() {
        given()
            .pathParam("name", "userid")
            .pathParam("val", "user123")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCookieEndpointInvalid() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "invalid")
            .pathParam("site", "abc.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given()
            .pathParam("i", 10)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDateParseEndpoint() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .pathParam("directory", "dir")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testNoTypeVarEndpoint() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "example")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testOrdered4Endpoint() {
        given()
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("y", "c")
            .pathParam("z", "d")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testRegexEndpoint() {
        given()
            .pathParam("txt", "test")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testPatEndpoint() {
        given()
            .pathParam("txt", "test")
            .pathParam("pat", "pattern")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testText2TxtEndpoint() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Dr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testCalcEndpointNotFound() {
        given()
            .pathParam("op", "nonexistent")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCookieEndpointNotFound() {
        given()
            .pathParam("name", "nonexistent")
            .pathParam("val", "nonexistent")
            .pathParam("site", "nonexistent.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCostfunsEndpointNotFound() {
        given()
            .pathParam("i", -1)
            .pathParam("s", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testDateParseEndpointNotFound() {
        given()
            .pathParam("dayname", "nonexistent")
            .pathParam("monthname", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffixEndpointNotFound() {
        given()
            .pathParam("directory", "nonexistent")
            .pathParam("file", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testNoTypeVarEndpointNotFound() {
        given()
            .pathParam("i", -1)
            .pathParam("s", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testOrdered4EndpointNotFound() {
        given()
            .pathParam("w", "nonexistent")
            .pathParam("x", "nonexistent")
            .pathParam("y", "nonexistent")
            .pathParam("z", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testRegexEndpointNotFound() {
        given()
            .pathParam("txt", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testPatEndpointNotFound() {
        given()
            .pathParam("txt", "nonexistent")
            .pathParam("pat", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testText2TxtEndpointNotFound() {
        given()
            .pathParam("word1", "nonexistent")
            .pathParam("word2", "nonexistent")
            .pathParam("word3", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testTitleEndpointNotFound() {
        given()
            .pathParam("sex", "nonexistent")
            .pathParam("title", "nonexistent")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(500);
    }
}
