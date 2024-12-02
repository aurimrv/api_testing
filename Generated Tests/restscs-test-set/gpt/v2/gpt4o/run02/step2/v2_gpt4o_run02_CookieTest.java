
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

public class v2_gpt4o_run02_CookieTest {

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
    public void testGetCookieValidUserId() {
        ValidatableResponse response = given()
            .pathParam("name", "userid")
            .pathParam("val", "user12345")
            .pathParam("site", "example.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));

        // Schema validation can be implemented in a custom way or using a library such as json-schema-validator
        // response.assertThat().body(matchesJsonSchemaInClasspath("cookie-schema.json"));
    }

    @Test
    public void testGetCookieInvalidUserId() {
        ValidatableResponse response = given()
            .pathParam("name", "userid")
            .pathParam("val", "us12345")
            .pathParam("site", "example.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));

        // Schema validation can be implemented in a custom way or using a library such as json-schema-validator
        // response.assertThat().body(matchesJsonSchemaInClasspath("cookie-schema.json"));
    }

    @Test
    public void testGetCookieValidSession() {
        ValidatableResponse response = given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));

        // Schema validation can be implemented in a custom way or using a library such as json-schema-validator
        // response.assertThat().body(matchesJsonSchemaInClasspath("cookie-schema.json"));
    }

    @Test
    public void testGetCookieInvalidSession() {
        ValidatableResponse response = given()
            .pathParam("name", "session")
            .pathParam("val", "pm")
            .pathParam("site", "abc.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));

        // Schema validation can be implemented in a custom way or using a library such as json-schema-validator
        // response.assertThat().body(matchesJsonSchemaInClasspath("cookie-schema.json"));
    }

    @Test
    public void testGetCookieWithInternalServerError() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "error.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCalcOperation() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("8"));
    }

    @Test
    public void testCostfuns() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("costfuns-schema.json"));
    }

    @Test
    public void testDateParse() {
        given()
            .pathParam("dayname", "monday")
            .pathParam("monthname", "january")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("dateparse-schema.json"));
    }

    @Test
    public void testFileSuffix() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "report.txt")
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("filesuffix-schema.json"));
    }

    @Test
    public void testNotyPevar() {
        given()
            .pathParam("i", 42)
            .pathParam("s", "example")
            .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("notypevar-schema.json"));
    }

    @Test
    public void testOrdered4() {
        given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("ordered4-schema.json"));
    }

    @Test
    public void testRegex() {
        given()
            .pathParam("txt", "test")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("regex-schema.json"));
    }

    @Test
    public void testPat() {
        given()
            .pathParam("txt", "test")
            .pathParam("pat", "pattern")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("pat-schema.json"));
    }

    @Test
    public void testText2Txt() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("text2txt-schema.json"));
    }

    @Test
    public void testTitle() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("title-schema.json"));
    }
}
