package org.restncs;

import  org.junit.AfterClass;
import  org.junit.BeforeClass;
import  org.junit.Before;
import  org.junit.Test;
import static org.junit.Assert.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;




/**
 * This file was automatically generated by EvoMaster on 2024-06-01T11:17:01.334496-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 62 tests
 * <br>
 * Covered targets: 659
 * <br>
 * Used time: 1h 0m 0s
 * <br>
 * Needed budget for current results: 72%
 * <br>
 * This file contains one example of each category of fault. The test cases in this file are a subset of the set of test cases likely to indicate faults.
 */
public class seed01_EvoMaster_fault_representatives_Test {

    
    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;
    /** [ems] - expectations master switch - is the variable that activates/deactivates expectations individual test cases
    * by default, expectations are turned off. The variable needs to be set to [true] to enable expectations
    */
    private static boolean ems = false;
    /**
    * sco - supported code oracle - checking that the response status code is among those supported according to the schema
    */
    private static boolean sco = false;
    
    
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
    
    
    
    
    @Test(timeout = 60000)
    public void test_0() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .get(baseUrlOfSut + "/v2/api-docs")
                .then()
                .statusCode(200);
        
        expectationHandler.expect(ems)
            /*
             Note: No supported codes appear to be defined. https://swagger.io/docs/specification/describing-responses/.
             This is somewhat unexpected, so the code below is likely to lead to a failed expectation
            */
            .that(sco, Arrays.asList().contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_1() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_26_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/-67107894/4369/-230?EMextraParam123=_EM_25_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_2() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_3_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/1889141939/-1073740827/347?EMextraParam123=_EM_2_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_3() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_43_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/107/15/-1047891")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_4() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/476/476/476")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(3.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_5() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/1889207459/938/347")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_6() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/0/0.16428127681913")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(5.164942350246511));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_7() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_116_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/0/0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(-1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_8() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_48_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/2061/17000/756")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_9() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/107/15/685")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_10() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_163_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/787/0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(-1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_11() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/77/0.0?EMextraParam123=_EM_309_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.013157894736842105));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_12() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_576_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/475/475/516")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_13() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/894/0.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_14() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/539/455/455?EMextraParam123=_EM_9429_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_15() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/970/273/794")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_16() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/452/873/452")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_17() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/567/344/854?EMextraParam123=_EM_202_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_18() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/634/484?EMextraParam123=42")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(150.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_19() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_1642_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/2141/-492")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(173.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_20() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/-2508/-19?EMextraParam123=_EM_508_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_21() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_509_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/-2407/29")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_22() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/-1069547174/962/7.778994370910224E8?EMextraParam123=_EM_33_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_23() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/968/364/0.23293647698336928")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_24() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_1_XYZ_")
                .get(baseUrlOfSut + "/api/fisher/538/333/0.48387795471206035")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_25() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/409/0/0.0?EMextraParam123=_EM_0_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", containsString("NaN"));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_26() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/374/1.0?EMextraParam123=_EM_90_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(9.836278131827101E-4));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_27() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/660/962/7.77899436623978E8?EMextraParam123=_EM_33_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.9999999999999999));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_28() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_5402_XYZ_")
                .get(baseUrlOfSut + "/api/expint/1/0.6559318025230759")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.40678848859033057));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_29() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/api/expint/1679571156/0.7023623476854792")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(2.9496433062980264E-10));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_30() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.679599894251/0.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_31() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_159_XYZ_")
                .get(baseUrlOfSut + "/api/expint/926/2.2996842635985226")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.081529922641645E-4));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_32() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/410/333/0.9677559094241207")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.37534679010247596));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_33() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/49/727/0.49072394773644834?EMextraParam123=_EM_35_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0012659472663367687));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_34() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_2660_XYZ_")
                .get(baseUrlOfSut + "/api/expint/6/0.17303040805013925")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.16133345392855936));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_35() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/8.988465674311579E307/1066.496630714539?EMextraParam123=_EM_294_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", containsString("NaN"));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_36() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.8052277714737137/0.005025175992452557?EMextraParam123=_EM_7_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.9849287212650446));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_37() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/4/1.26953125E-16")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(6.76460369201474E-67));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_38() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_988_XYZ_")
                .get(baseUrlOfSut + "/api/bessj/10/-10.0?EMextraParam123=_EM_987_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.20748610663402065));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_39() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/894/-0.5919386991003808")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_40() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_368_XYZ_")
                .get(baseUrlOfSut + "/api/gammq/0.1/1.1?EMextraParam123=_EM_367_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.020600769628094647));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_41() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_308_XYZ_")
                .get(baseUrlOfSut + "/api/gammq/0.36610092730524113/137.05355643132071?EMextraParam123=_EM_307_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(5.447783389306318E-62));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_42() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_988_XYZ_")
                .get(baseUrlOfSut + "/api/bessj/3/-6.893692557189304?EMextraParam123=_EM_987_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.14222253689748018));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_43() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/287/799509.7846861759?EMextraParam123=_EM_36_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(8.133201166332833E-4));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_44() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/3/-8.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.29113220692602515));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_45() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/16794/333/0.96776")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_46() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_73_XYZ_")
                .get(baseUrlOfSut + "/api/bessj/-1047884/0.0?EMextraParam123=_EM_72_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_47() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/33422/372?EMextraParam123=_EM_315_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_48() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/394/8388941/0.9677559094241207?EMextraParam123=_EM_0_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_49() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/268436350/0.864?EMextraParam123=_EM_29_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_50() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/-513198971/961?EMextraParam123=_EM_92_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_51() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/153/2091322441?EMextraParam123=_EM_96_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_52() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/343/-8388555")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_53() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/-2097596092/0.9268034058220779")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_54() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/814/-2.0028873621701393E8?EMextraParam123=_EM_54_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_55() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.53412264528006/-41.80148699891073")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_56() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/-24317.720587260133/137.05355643132071?EMextraParam123=_EM_307_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_57() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_368_XYZ_")
                .get(baseUrlOfSut + "/api/gammq/0.0/1.109055844781")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_58() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_67_XYZ_")
                .get(baseUrlOfSut + "/api/expint/0/0.0?EMextraParam123=_EM_66_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_59() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_5230_XYZ_")
                .get(baseUrlOfSut + "/api/expint/1/0.0?EMextraParam123=42")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_60() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/5.40712388220306E307/5.077956438075438E307")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_61() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/8.988465674311579E307/8.988465674311579E307?EMextraParam123=_EM_367_XYZ_")
                .then()
                .statusCode(400)
                .assertThat()
                .body(isEmptyOrNullString());
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }


}
