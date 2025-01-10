package em.embedded.market; //SEED 04

import  org.junit.jupiter.api.AfterAll;
import  org.junit.jupiter.api.BeforeAll;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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
 * This file was automatically generated by EvoMaster on 2024-05-02T19:35:15.173-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 8 tests
 * <br>
 * Covered targets: 261
 * <br>
 * Used time: 1h 0m 7s
 * <br>
 * Needed budget for current results: 83%
 * <br>
 * This file contains test cases that are likely to indicate faults.
 */
public class seed04_EvoMaster_faults_Test {

    
    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;
    /** [ems] - expectations master switch - is the variable that activates/deactivates expectations individual test cases
    * by default, expectations are turned off. The variable needs to be set to [true] to enable expectations
    */
    private static boolean ems = false;
    /**
    * sco - supported code oracle - checking that the response status code is among those supported according to the schema
    */
    private static boolean sco = false;
    
    
    @BeforeAll
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
    
    
    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }
    
    
    @BeforeEach
    public void initTest() {
        controller.resetDatabase(Arrays.asList("CUSTOMER_ORDER","CART","CONTACTS","USER_ROLE","PRODUCT","ORDERED_PRODUCT","BILL","DISTILLERY","USER_ACCOUNT"));
        controller.resetStateOfSUT();
    }
    
    
    
    
    /**
    * [test_0_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_0_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [], " + 
                    " \"password\": \"Tz\", " + 
                    " \"phone\": \"\" " + 
                    " } ")
                .post(baseUrlOfSut + "/register?EMextraParam123=_EM_25_XYZ_")
                .then()
                .statusCode(500) // market/rest/exception/RestErrorResponse_50_getFieldErrors
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("JSON parse error: Expected relation name; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Expected relation name\n at [Source: (ByteArrayInputStream); line: 1, column: 13] (through reference chain: market.dto.UserDTO[\"_links\"])"))
                .body("'description'", containsString("uri=/register"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_1_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_1_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_232_XYZ_")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"hreflang\": \"x\", " + 
                    " \"media\": \"_EM_201_XYZ_\", " + 
                    " \"title\": \"_EM_205_XYZ_\", " + 
                    " \"type\": \"_EM_206_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"hreflang\": \"_EM_209_XYZ_\", " + 
                    " \"media\": \"_EM_210_XYZ_\", " + 
                    " \"name\": \"_EM_211_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_213_XYZ_\": \"_EM_214_XYZ_\", " + 
                    " \"_EM_215_XYZ_\": \"_EM_216_XYZ_\", " + 
                    " \"A1Z9hRX4jHTt\": \"_EM_217_XYZ_\", " + 
                    " \"_EM_218_XYZ_\": \"_EM_219_XYZ_\" " + 
                    " }, " + 
                    " \"type\": \"_EM_221_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"href\": \"_EM_223_XYZ_\", " + 
                    " \"hreflang\": \"_EM_224_XYZ_\", " + 
                    " \"media\": \"_EM_225_XYZ_\", " + 
                    " \"name\": \"_EM_226_XYZ_\", " + 
                    " \"rel\": {}, " + 
                    " \"title\": \"_EM_228_XYZ_\" " + 
                    " } " + 
                    " ], " + 
                    " \"address\": \"_EM_230_XYZ_\", " + 
                    " \"phone\": \"\" " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/contacts?EMextraParam123=_EM_231_XYZ_")
                .then()
                .statusCode(500) // market/rest/exception/RestErrorResponse_50_getFieldErrors
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("JSON parse error: Expected relation name; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Expected relation name\n at [Source: (ByteArrayInputStream); line: 1, column: 13] (through reference chain: market.dto.ContactsDTO[\"_links\"])"))
                .body("'description'", containsString("uri=/customer/contacts"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_2_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_2_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_156_XYZ_")
                .get(baseUrlOfSut + "/customer/contacts?EMextraParam123=_EM_155_XYZ_")
                .then()
                .statusCode(500) // market/dto/assembler/ContactsDtoAssembler_12_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/contacts"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_3_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_3_with500() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("USER_ROLE", 3394L)
                .d("USER_ID", "0")
                .d("ROLE_ID", "325")
            .and().insertInto("USER_ROLE", 3395L)
                .d("USER_ID", "1049467")
                .d("ROLE_ID", "3166255413906102786")
            .and().insertInto("USER_ROLE", 3396L)
                .d("USER_ID", "-131198")
                .d("ROLE_ID", "-130788")
            .and().insertInto("CUSTOMER_ORDER", 3397L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "1057615")
                .d("DATE_CREATED", "\"2063-07-30\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "399")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "-261690")
            .and().insertInto("CUSTOMER_ORDER", 3398L)
                .d("ID", "905")
                .d("USER_ACCOUNT_ID", "351")
                .d("DATE_CREATED", "\"2023-06-05\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "-524769")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "-3616")
            .and().insertInto("PRODUCT", 3399L)
                .d("NAME", "\"WuTxh\"")
                .d("DISTILLERY_ID", "0")
                .d("AGE", "-25")
                .d("ALCOHOL", "3113.5392094786066")
                .d("VOLUME", "514")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"CY3j\"")
                .d("AVAILABLE", "false")
            .and().insertInto("PRODUCT", 3400L)
                .d("NAME", "\"DYKXOyN4p\"")
                .d("DISTILLERY_ID", "5130019728505405900")
                .d("AGE", "-742")
                .d("ALCOHOL", "0.0")
                .d("VOLUME", "0")
                .d("PRICE", "-0.16273526394636795")
                .d("DESCRIPTION", "NULL")
                .d("AVAILABLE", "true")
            .and().insertInto("PRODUCT", 3401L)
                .d("NAME", "\"3\"")
                .d("DISTILLERY_ID", "-8461371535280823690")
                .d("AGE", "343")
                .d("ALCOHOL", "-11832.54597062197")
                .d("VOLUME", "517")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"_EM_11738_XYZ_\"")
                .d("AVAILABLE", "true")
            .and().insertInto("PRODUCT", 3402L)
                .d("NAME", "\"dnWC_pWhTz1Y\"")
                .d("DISTILLERY_ID", "385")
                .d("AGE", "-31973")
                .d("ALCOHOL", "-9191.607298378747")
                .d("VOLUME", "0")
                .d("PRICE", "0.7576390593137129")
                .d("DESCRIPTION", "NULL")
                .d("AVAILABLE", "true")
            .and().insertInto("PRODUCT", 3403L)
                .d("NAME", "\"_EM_11731_XYZ_\"")
                .d("DISTILLERY_ID", "-9081080852973666294")
                .d("AGE", "NULL")
                .d("ALCOHOL", "0.22849050820641037")
                .d("VOLUME", "884")
                .d("PRICE", "-0.3355875349882522")
                .d("DESCRIPTION", "\"_EM_11725_XYZ_\"")
                .d("AVAILABLE", "true")
            .and().insertInto("DISTILLERY", 3405L)
                .d("TITLE", "\"_EM_11726_XYZ_\"")
                .d("REGION_ID", "0")
                .d("DESCRIPTION", "\"i\"")
            .and().insertInto("DISTILLERY", 3406L)
                .d("TITLE", "\"Iu8xf8NnFyvFz7\"")
                .d("REGION_ID", "0")
                .d("DESCRIPTION", "\"_EM_11727_XYZ_\"")
            .and().insertInto("USER_ACCOUNT", 3774L)
                .d("EMAIL", "\"ThL3t\"")
                .d("PASSWORD", "\"2\"")
                .d("NAME", "\"0ragxHcDna6\"")
                .d("ACTIVE", "true")
            .and().insertInto("USER_ACCOUNT", 3775L)
                .d("EMAIL", "\"ctEVk4PBWtsNuy9\"")
                .d("PASSWORD", "\"pBu94FkhDctBx\"")
                .d("NAME", "NULL")
                .d("ACTIVE", "true")
            .and().insertInto("USER_ACCOUNT", 3776L)
                .d("EMAIL", "\"SDl\"")
                .d("PASSWORD", "NULL")
                .d("NAME", "NULL")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 3777L)
                .d("EMAIL", "\"6\"")
                .d("PASSWORD", "NULL")
                .d("NAME", "NULL")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 3778L)
                .d("EMAIL", "\"Nt7vwdz4PW_jaj5\"")
                .d("PASSWORD", "\"2QCnZbbz8Q45jyqF\"")
                .d("NAME", "NULL")
                .d("ACTIVE", "false")
            .and().insertInto("CART", 2318L)
                .d("TOTAL_ITEMS", "-261715")
                .d("PRODUCTS_COST", "524841")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("PRODUCT", 2319L)
                .d("NAME", "NULL")
                .d("DISTILLERY_ID", "-4611686018427388864")
                .d("AGE", "1177")
                .d("ALCOHOL", "0.7484159464850058")
                .d("VOLUME", "702")
                .d("PRICE", "-0.28437744461556386")
                .d("DESCRIPTION", "\"_EM_11739_XYZ_\"")
                .d("AVAILABLE", "false")
            .and().insertInto("CUSTOMER_ORDER", 2320L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "506")
                .d("DATE_CREATED", "\"1989-04-18\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "441")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "70")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"href\": \"_EM_9745_XYZ_\", " + 
                    " \"profile\": \"_EM_9748_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_9749_XYZ_\": \"DTW\", " + 
                    " \"_EM_9750_XYZ_\": \"_EM_9751_XYZ_\" " + 
                    " }, " + 
                    " \"title\": \"_EM_9752_XYZ_\", " + 
                    " \"type\": \"ytjZpTLFh_Di75_\" " + 
                    " }, " + 
                    " { " + 
                    " \"href\": \"_EM_9754_XYZ_\", " + 
                    " \"name\": \"_EM_9755_XYZ_\", " + 
                    " \"profile\": \"_fjVa8GqispxkF\", " + 
                    " \"rel\": { " + 
                    " \"Me\": \"gyAOpA03SzIwbF\", " + 
                    " \"qNQhLWmyFwfMyGw\": \"_EM_9756_XYZ_\", " + 
                    " \"zzT97nfO\": \"_EM_9757_XYZ_\", " + 
                    " \"_EM_9758_XYZ_\": \"_EM_9759_XYZ_\" " + 
                    " }, " + 
                    " \"title\": \"_EM_9760_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_9762_XYZ_\", " + 
                    " \"href\": \"_EM_9763_XYZ_\", " + 
                    " \"name\": \"_EM_9766_XYZ_\", " + 
                    " \"profile\": \"_EM_9767_XYZ_\", " + 
                    " \"title\": \"78h9f3UhYZFR9Huz\", " + 
                    " \"type\": \"XhaSLj\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_9772_XYZ_\", " + 
                    " \"hreflang\": \"_EM_9773_XYZ_\", " + 
                    " \"name\": \"g2h1g\", " + 
                    " \"profile\": \"IhPLViZDOj\", " + 
                    " \"rel\": { " + 
                    " \"XYwgK\": \"_EM_9775_XYZ_\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_9776_XYZ_\", " + 
                    " \"media\": \"_EM_9778_XYZ_\", " + 
                    " \"name\": \"O\" " + 
                    " } " + 
                    " ], " + 
                    " \"quantity\": 2605 " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/cart")
                .then()
                .statusCode(500) // market/rest/exception/RestErrorResponse_50_getFieldErrors
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("JSON parse error: Expected relation name; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Expected relation name\n at [Source: (ByteArrayInputStream); line: 1, column: 13] (through reference chain: market.dto.CartItemDTO[\"_links\"])"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_4_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_4_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/products")
                .then()
                .statusCode(500) // market/service/impl/ProductServiceImpl_35_findAll
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Unable to find market.domain.Distillery with id 0; nested exception is javax.persistence.EntityNotFoundException: Unable to find market.domain.Distillery with id 0"))
                .body("'description'", containsString("uri=/products"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_5_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_5_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_161_XYZ_")
                .get(baseUrlOfSut + "/customer")
                .then()
                .statusCode(500) // market/dto/assembler/UserAccountDtoAssembler_16_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_6_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_6_with500() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 1077L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"kUgANtb1T9FGu3Z\"")
                .d("CITY_REGION", "NULL")
            .and().insertInto("CONTACTS", 1078L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"2Mt\"")
                .d("CITY_REGION", "\"Wm\"")
            .and().insertInto("CONTACTS", 1079L)
                .d("PHONE", "\"\"")
                .d("ADDRESS", "\"Z4r\"")
                .d("CITY_REGION", "\"v7H\"")
            .and().insertInto("CART", 8027L)
                .d("TOTAL_ITEMS", "286")
                .d("PRODUCTS_COST", "796292659")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 8028L)
                .d("TOTAL_ITEMS", "429")
                .d("PRODUCTS_COST", "398")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 8029L)
                .d("TOTAL_ITEMS", "603")
                .d("PRODUCTS_COST", "622")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 8030L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "814")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 8031L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CUSTOMER_ORDER", 8044L)
                .d("ID", "610")
                .d("USER_ACCOUNT_ID", "-4020971392479202558")
                .d("DATE_CREATED", "NULL")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "311")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "-1985492739")
            .and().insertInto("CUSTOMER_ORDER", 8045L)
                .d("ID", "173")
                .d("USER_ACCOUNT_ID", "5826123394203104601")
                .d("DATE_CREATED", "\"2067-08-12\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "316")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "864")
            .and().insertInto("CUSTOMER_ORDER", 8046L)
                .d("ID", "854")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"2095-01-01\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "580")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "403")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders/854?" + 
                    "name=&" + 
                    "included=qc1SohC")
                .then()
                .statusCode(500) // market/dto/assembler/OrderDtoAssembler_17_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/orders/854"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_7_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_7_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders?name=_EM_695_XYZ_")
                .then()
                .statusCode(500) // market/dto/assembler/OrderDtoAssembler_18_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/orders"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }


}
