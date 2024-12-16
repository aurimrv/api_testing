package market;

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
 * This file was automatically generated by EvoMaster on 2024-05-02T18:26:58.146-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 8 tests
 * <br>
 * Covered targets: 219
 * <br>
 * Used time: 1h 0m 6s
 * <br>
 * Needed budget for current results: 95%
 * <br>
 * This file contains test cases that are likely to indicate faults.
 */
public class seed03_EvoMaster_faults_Test {

    
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
        controller.resetDatabase(Arrays.asList("CONTACTS","CUSTOMER_ORDER","CART","USER_ACCOUNT","PRODUCT"));
        controller.resetStateOfSUT();
    }
    
    
    
    
    /**
    * [test_0_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_0_with500() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 304L)
                .d("PHONE", "\"_EM_9465_XYZ_\"")
                .d("ADDRESS", "\"AO5r_t0BTHvXu\"")
                .d("CITY_REGION", "\"2147483647\"")
            .and().insertInto("CONTACTS", 305L)
                .d("PHONE", "\"EfanmEC8Cr9\"")
                .d("ADDRESS", "\"DESCRIPT5_7_2_\"")
                .d("CITY_REGION", "\"_EM_4040_XYZ_\"")
            .and().insertInto("CUSTOMER_ORDER", 420L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "-262144")
                .d("DATE_CREATED", "\"2075-06-01\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "937")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "21389")
            .and().insertInto("CART", 421L)
                .d("TOTAL_ITEMS", "0")
                .d("PRODUCTS_COST", "201")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 422L)
                .d("TOTAL_ITEMS", "-467")
                .d("PRODUCTS_COST", "-1908")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 423L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "131072")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 424L)
                .d("TOTAL_ITEMS", "-16776799")
                .d("PRODUCTS_COST", "0")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 425L)
                .d("TOTAL_ITEMS", "66426")
                .d("PRODUCTS_COST", "0")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("USER_ACCOUNT", 2401L)
                .d("EMAIL", "\"_EM_9466_XYZ_\"")
                .d("PASSWORD", "\"6xNgVY\"")
                .d("NAME", "\"MVX\"")
                .d("ACTIVE", "true")
            .and().insertInto("USER_ACCOUNT", 2402L)
                .d("EMAIL", "\"EM9Jix6k9gFahzo\"")
                .d("PASSWORD", "\"Ofc\"")
                .d("NAME", "\"_EM_7600_XYZ_\"")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2403L)
                .d("EMAIL", "\"vuAFYycxoq6O7fa\"")
                .d("PASSWORD", "\"^fyvgBWq\"")
                .d("NAME", "\"j3\"")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2404L)
                .d("EMAIL", "\"R29tEYlkU\"")
                .d("PASSWORD", "\"1DHJXcNpK\"")
                .d("NAME", "\"qo9GgVK5bo5\"")
                .d("ACTIVE", "true")
            .and().insertInto("PRODUCT", 2736L)
                .d("NAME", "\"QULUM3r\"")
                .d("DISTILLERY_ID", "550")
                .d("AGE", "-16232")
                .d("ALCOHOL", "0.657")
                .d("VOLUME", "673")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"KdMw0xSKRH85\"")
                .d("AVAILABLE", "false")
            .and().insertInto("PRODUCT", 2737L)
                .d("NAME", "NULL")
                .d("DISTILLERY_ID", "0")
                .d("AGE", "NULL")
                .d("ALCOHOL", "0.26904656021502427")
                .d("VOLUME", "NULL")
                .d("PRICE", "0.40785082507429526")
                .d("DESCRIPTION", "\"cu1ToKvMqoaG\"")
                .d("AVAILABLE", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"deprecation\": \"\", " + 
                    " \"href\": \"T6t0ooSGtFx\", " + 
                    " \"name\": \"Vj9GwBI3mAxn\", " + 
                    " \"rel\": { " + 
                    " \"68FnQKU\": \"gGLL8mk\", " + 
                    " \"sAvw\": \"_vIaX\", " + 
                    " \"CKL\": \"zi_fYYEKghf3\", " + 
                    " \"TnLD20EUhadL\": \"au8f2WTV\", " + 
                    " \"\": \"AtPr6hogM\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"media\": \"2HkHYooUc\", " + 
                    " \"profile\": \"7hYODg_dM2_L0\", " + 
                    " \"rel\": { " + 
                    " \"XljCqNcQO\": \"bWuLz52v\", " + 
                    " \"qT6b\": \"iyehKtxAMaO2\", " + 
                    " \"vaSmFKGmdU\": \"ZyoY1WrWfSB\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"q\", " + 
                    " \"hreflang\": \"T5B0kMm2ZLz\", " + 
                    " \"media\": \"E_B3hi\", " + 
                    " \"name\": \"FSor\", " + 
                    " \"profile\": \"ZuFrM9_cF\", " + 
                    " \"rel\": { " + 
                    " \"j7PaVEFQHV\": \"hWy77Y1ZlgAgGBJL\" " + 
                    " }, " + 
                    " \"title\": \"tyPPZSgogIHqnPIV\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"SQbZt\", " + 
                    " \"hreflang\": \"IvlklUx7SyuKyDh\", " + 
                    " \"profile\": \"_JZa6LHqnH\", " + 
                    " \"rel\": { " + 
                    " \"zH5l\": \"swPshnZYlt\", " + 
                    " \"HC\": \"lnpb\", " + 
                    " \"bzopdSU\": \"CoLMUmYaSZ\", " + 
                    " \"Xk5l\": \"Xxzvj\" " + 
                    " }, " + 
                    " \"type\": \"VPub2b7C3Bbiy3Oj\" " + 
                    " }, " + 
                    " { " + 
                    " \"href\": \"Phedvua7\", " + 
                    " \"hreflang\": \"BB2dYD3XQXH7vu2\", " + 
                    " \"name\": \"_466J\", " + 
                    " \"profile\": \"GdI_PUW\", " + 
                    " \"title\": \"wrH_0nhkmkwbjVgM\" " + 
                    " } " + 
                    " ], " + 
                    " \"address\": \"6\", " + 
                    " \"email\": \"T384SOmgm\", " + 
                    " \"name\": \"p\", " + 
                    " \"phone\": \"\" " + 
                    " } ")
                .post(baseUrlOfSut + "/register")
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
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 304L)
                .d("PHONE", "\"_EM_9465_XYZ_\"")
                .d("ADDRESS", "\"AO5r_t0BTHvXu\"")
                .d("CITY_REGION", "\"2147483647\"")
            .and().insertInto("CONTACTS", 305L)
                .d("PHONE", "\"EfanmEC8Cr9\"")
                .d("ADDRESS", "\"DESCRIPT5_7_2_\"")
                .d("CITY_REGION", "\"_EM_4040_XYZ_\"")
            .and().insertInto("CUSTOMER_ORDER", 420L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "-262144")
                .d("DATE_CREATED", "\"2075-06-01\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "937")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "21389")
            .and().insertInto("CART", 421L)
                .d("TOTAL_ITEMS", "0")
                .d("PRODUCTS_COST", "201")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 422L)
                .d("TOTAL_ITEMS", "-467")
                .d("PRODUCTS_COST", "-1908")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 423L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "131072")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 424L)
                .d("TOTAL_ITEMS", "-16776799")
                .d("PRODUCTS_COST", "0")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 425L)
                .d("TOTAL_ITEMS", "66426")
                .d("PRODUCTS_COST", "0")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("USER_ACCOUNT", 2401L)
                .d("EMAIL", "\"_EM_9466_XYZ_\"")
                .d("PASSWORD", "\"6xNgVY\"")
                .d("NAME", "\"MVX\"")
                .d("ACTIVE", "true")
            .and().insertInto("USER_ACCOUNT", 2402L)
                .d("EMAIL", "\"EM9Jix6k9gFahzo\"")
                .d("PASSWORD", "\"Ofc\"")
                .d("NAME", "\"_EM_7600_XYZ_\"")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2403L)
                .d("EMAIL", "\"vuAFYycxoq6O7fa\"")
                .d("PASSWORD", "\"^fyvgBWq\"")
                .d("NAME", "\"j3\"")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2404L)
                .d("EMAIL", "\"R29tEYlkU\"")
                .d("PASSWORD", "\"1DHJXcNpK\"")
                .d("NAME", "\"qo9GgVK5bo5\"")
                .d("ACTIVE", "true")
            .and().insertInto("PRODUCT", 2736L)
                .d("NAME", "\"QULUM3r\"")
                .d("DISTILLERY_ID", "550")
                .d("AGE", "-16232")
                .d("ALCOHOL", "0.657")
                .d("VOLUME", "673")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"KdMw0xSKRH85\"")
                .d("AVAILABLE", "false")
            .and().insertInto("PRODUCT", 2737L)
                .d("NAME", "NULL")
                .d("DISTILLERY_ID", "0")
                .d("AGE", "NULL")
                .d("ALCOHOL", "0.26904656021502427")
                .d("VOLUME", "NULL")
                .d("PRICE", "0.40785082507429526")
                .d("DESCRIPTION", "\"cu1ToKvMqoaG\"")
                .d("AVAILABLE", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"deprecation\": \"\", " + 
                    " \"href\": \"T6t0ooSGtFx\", " + 
                    " \"name\": \"Vj9GwBI3mAxn\", " + 
                    " \"rel\": { " + 
                    " \"68FnQKU\": \"gGLL8mk\", " + 
                    " \"sAvw\": \"_vIaX\", " + 
                    " \"CKL\": \"zi_fYYEKghf3\", " + 
                    " \"TnLD20EUhadL\": \"au8f2WTV\", " + 
                    " \"\": \"AtPr6hogM\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"media\": \"2HkHYooUc\", " + 
                    " \"profile\": \"7hYODg_dM2_L0\", " + 
                    " \"rel\": { " + 
                    " \"XljCqNcQO\": \"bWuLz52v\", " + 
                    " \"qT6b\": \"iyehKtxAMaO2\", " + 
                    " \"vaSmFKGmdU\": \"ZyoY1WrWfSB\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"q\", " + 
                    " \"hreflang\": \"T5B0kMm2ZLz\", " + 
                    " \"media\": \"E_B3hi\", " + 
                    " \"name\": \"FSor\", " + 
                    " \"profile\": \"ZuFrM9_cF\", " + 
                    " \"rel\": { " + 
                    " \"j7PaVEFQHV\": \"hWy77Y1ZlgAgGBJL\" " + 
                    " }, " + 
                    " \"title\": \"tyPPZSgogIHqnPIV\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"SQbZt\", " + 
                    " \"hreflang\": \"IvlklUx7SyuKyDh\", " + 
                    " \"profile\": \"_JZa6LHqnH\", " + 
                    " \"rel\": { " + 
                    " \"zH5l\": \"swPshnZYlt\", " + 
                    " \"HC\": \"lnpb\", " + 
                    " \"bzopdSU\": \"CoLMUmYaSZ\", " + 
                    " \"Xk5l\": \"Xxzvj\" " + 
                    " }, " + 
                    " \"type\": \"VPub2b7C3Bbiy3Oj\" " + 
                    " }, " + 
                    " { " + 
                    " \"href\": \"Phedvua7\", " + 
                    " \"hreflang\": \"BB2dYD3XQXH7vu2\", " + 
                    " \"name\": \"_466J\", " + 
                    " \"profile\": \"GdI_PUW\", " + 
                    " \"title\": \"wrH_0nhkmkwbjVgM\" " + 
                    " } " + 
                    " ], " + 
                    " \"address\": \"6\" " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/contacts")
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
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_2_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_517_XYZ_")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"hreflang\": \"_EM_473_XYZ_\", " + 
                    " \"profile\": \"_EM_476_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_477_XYZ_\": \"_EM_478_XYZ_\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"media\": \"h6_QQFFiM\", " + 
                    " \"name\": \"_EM_484_XYZ_\", " + 
                    " \"profile\": \"_EM_485_XYZ_\", " + 
                    " \"title\": \"_EM_492_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_494_XYZ_\", " + 
                    " \"hreflang\": \"_EM_496_XYZ_\", " + 
                    " \"media\": \"_EM_497_XYZ_\", " + 
                    " \"name\": \"_EM_498_XYZ_\", " + 
                    " \"title\": \"_EM_500_XYZ_\", " + 
                    " \"type\": \"_EM_501_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"hreflang\": \"_EM_503_XYZ_\", " + 
                    " \"media\": \"_EM_504_XYZ_\", " + 
                    " \"profile\": \"_EM_506_XYZ_\", " + 
                    " \"title\": \"_EM_514_XYZ_\" " + 
                    " } " + 
                    " ], " + 
                    " \"productId\": 134, " + 
                    " \"quantity\": 674 " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/cart?name=_EM_470_XYZ_")
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
    * [test_3_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_3_with500() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 304L)
                .d("PHONE", "\"HPJgqeidN0\"")
                .d("ADDRESS", "\"MrC\"")
                .d("CITY_REGION", "\"F\"")
            .and().insertInto("CONTACTS", 305L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"Fs\"")
                .d("CITY_REGION", "\"hlrUlz54U\"")
            .and().insertInto("CUSTOMER_ORDER", 420L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "1")
                .d("DATE_CREATED", "\"2087-01-09\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "909")
            .and().insertInto("CART", 421L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "169")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 422L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "140")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 423L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "478")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 424L)
                .d("TOTAL_ITEMS", "417")
                .d("PRODUCTS_COST", "796")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 425L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "NULL")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders?included=W58Mp")
                .then()
                .statusCode(500) // market/service/impl/OrderServiceImpl_45_getUserOrders
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Null value was assigned to a property [class market.domain.Order.productsCost] of primitive type setter of market.domain.Order.productsCost; nested exception is org.hibernate.PropertyAccessException: Null value was assigned to a property [class market.domain.Order.productsCost] of primitive type setter of market.domain.Order.productsCost"))
                .body("'description'", containsString("uri=/customer/orders"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_4_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_4_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/contacts?EMextraParam123=_EM_194_XYZ_")
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
    * [test_5_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_5_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_202_XYZ_")
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
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 304L)
                .d("PHONE", "\"L4cUBURq\"")
                .d("ADDRESS", "\"_EM_4039_XYZ_\"")
                .d("CITY_REGION", "\"Fs3F\"")
            .and().insertInto("CONTACTS", 305L)
                .d("PHONE", "\"_EM_11513_XYZ_\"")
                .d("ADDRESS", "\"_EM_7108_XYZ_\"")
                .d("CITY_REGION", "\"_EM_4040_XYZ_\"")
            .and().insertInto("CUSTOMER_ORDER", 420L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"2071-05-05\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "-15447")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "19341")
            .and().insertInto("CART", 421L)
                .d("TOTAL_ITEMS", "-2090706436")
                .d("PRODUCTS_COST", "201")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 422L)
                .d("TOTAL_ITEMS", "45")
                .d("PRODUCTS_COST", "-1908")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 423L)
                .d("TOTAL_ITEMS", "783")
                .d("PRODUCTS_COST", "-128")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 424L)
                .d("TOTAL_ITEMS", "-16776799")
                .d("PRODUCTS_COST", "-4198400")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 425L)
                .d("TOTAL_ITEMS", "898")
                .d("PRODUCTS_COST", "-839371713")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("USER_ACCOUNT", 2392L)
                .d("EMAIL", "\"Ysrl_NXI2\"")
                .d("PASSWORD", "\"6yaw4GL9sorigAT\"")
                .d("NAME", "NULL")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2393L)
                .d("EMAIL", "\"k59xxO45\"")
                .d("PASSWORD", "\"_EM_11514_XYZ_\"")
                .d("NAME", "NULL")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 2394L)
                .d("EMAIL", "\"_EM_11515_XYZ_\"")
                .d("PASSWORD", "\"U\"")
                .d("NAME", "\"AgsVNH1EO5pm\"")
                .d("ACTIVE", "true")
            .and().insertInto("PRODUCT", 2409L)
                .d("NAME", "\"_EM_7425_XYZ_\"")
                .d("DISTILLERY_ID", "NULL")
                .d("AGE", "854")
                .d("ALCOHOL", "0.6365755212110151")
                .d("VOLUME", "254")
                .d("PRICE", "0.9299451472075089")
                .d("DESCRIPTION", "\"_EM_11516_XYZ_\"")
                .d("AVAILABLE", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders/0?name=_EM_7421_XYZ_")
                .then()
                .statusCode(500) // market/dto/assembler/OrderDtoAssembler_17_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/orders/0"))
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
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 2325L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"C0WAJkVv\"")
                .d("CITY_REGION", "NULL")
            .and().insertInto("CONTACTS", 2326L)
                .d("PHONE", "\"DF\"")
                .d("ADDRESS", "\"i27n0\"")
                .d("CITY_REGION", "NULL")
            .and().insertInto("CUSTOMER_ORDER", 10814L)
                .d("ID", "1128177350300132335")
                .d("USER_ACCOUNT_ID", "1")
                .d("DATE_CREATED", "\"2048-01-15\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "641")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "109")
            .and().insertInto("CUSTOMER_ORDER", 10815L)
                .d("ID", "728")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"2074-04-13\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "831")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "975")
            .and().insertInto("CUSTOMER_ORDER", 10816L)
                .d("ID", "835")
                .d("USER_ACCOUNT_ID", "234")
                .d("DATE_CREATED", "NULL")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "925")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "869")
            .and().insertInto("CUSTOMER_ORDER", 10817L)
                .d("ID", "2059406478")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"1908-05-17\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "735")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "534")
            .and().insertInto("CART", 10818L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "634")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 10819L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 10820L)
                .d("TOTAL_ITEMS", "146")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 10821L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 10822L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders")
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
