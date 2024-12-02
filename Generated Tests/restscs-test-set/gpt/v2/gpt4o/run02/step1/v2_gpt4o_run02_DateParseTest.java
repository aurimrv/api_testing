
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

public class v2_gpt4o_run02_DateParseTest {

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
    public void testDateParseValidInputs() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParseInvalidDayname() {
        given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidMonthname() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "invalid")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidInputs() {
        given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "invalid")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testDateParseInternalServerError() {
        // Simulate a server error by sending invalid input that breaks the server
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", null)
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testDateParseSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200);

        response.assertThat().body(matchesJsonSchemaInClasspath("dateParseSchema.json"));
    }

    @Test
    public void testCalcEndpoint() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("15"));
    }

    @Test
    public void testCalcInvalidOperation() {
        given()
            .pathParam("op", "invalid")
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given()
            .pathParam("name", "sessionId")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("Cost function result"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testNotypevarEndpoint() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("Notypevar result"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given()
            .pathParam("w", "first")
            .pathParam("x", "second")
            .pathParam("y", "third")
            .pathParam("z", "fourth")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("Ordered4 result"));
    }

    @Test
    public void testPatEndpoint() {
        given()
            .pathParam("txt", "test")
            .pathParam("pat", ".*")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Match found"));
    }

    @Test
    public void testText2txtEndpoint() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Text2txt result"));
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title result"));
    }
}
