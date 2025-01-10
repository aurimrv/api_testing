package em.embedded.market;
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
 * This file was automatically generated by EvoMaster on 2024-05-03T14:12:56.936-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 10 tests
 * <br>
 * Covered targets: 644
 * <br>
 * Used time: 1h 0m 6s
 * <br>
 * Needed budget for current results: 91%
 * <br>
 * This file contains test cases that represent successful calls.
 */
public class seed06_EvoMaster_successes_Test {

    
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
    /**
    * rso - response structure oracle - checking that the response objects match the responses defined in the schema
    */
    private static boolean rso = false;
    
    
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","PRODUCT","CUSTOMER_ORDER","USER_ACCOUNT","CART","cart_item"));
        controller.resetStateOfSUT();
    }
    
    
    
    
    @Test @Timeout(60)
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
    
    
    @Test @Timeout(60)
    public void test_1() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "_EM_460_XYZ_")
                .get(baseUrlOfSut + "/customer/contacts?name=_EM_458_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'phone'", containsString("+7 123 456 78 90"))
                .body("'address'", containsString("Riesstrasse 18"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_2() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/products?EMextraParam123=_EM_17_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("size()", equalTo(11))
                .body("[0].'productId'", numberMatches(1.0))
                .body("[0].'distillery'", containsString("Ardbeg"))
                .body("[0].'name'", containsString("Ten"))
                .body("[0].'price'", numberMatches(4420.0))
                .body("[0].'age'", numberMatches(10.0))
                .body("[0].'volume'", numberMatches(700.0))
                .body("[0].'alcohol'", numberMatches(46.0))
                .body("[0].'description'", containsString("Ten Years Old is the basis of the Ardbeg range. After 10 years of maturation in ex-bourbon casks, the whiskey bottled at 46% ABV without cold filtering. The characteristic peat, although clearly present but in perfect balance with the natural sweetness and not predominant in the taste."))
                .body("[0].'available'", equalTo(false))
                .body("[0].'links'.size()", equalTo(1))
                .body("[0].'links'[0].'rel'", containsString("self"))
                .body("[1].'productId'", numberMatches(2.0))
                .body("[1].'distillery'", containsString("Ardbeg"))
                .body("[1].'name'", containsString("Uigeadail"))
                .body("[1].'price'", numberMatches(7020.0))
                .body("[1].'age'", numberMatches(0.0))
                .body("[1].'volume'", numberMatches(700.0))
                .body("[1].'alcohol'", numberMatches(54.2))
                .body("[1].'description'", containsString("Ardbeg Uigedael is named after Loch Uigedael, the lake in the grounds of the distillery Ardbeg which are the water is an important factor in the distilling process. The Uigedael a vatted malt, bottled at 54.2% ABV without cold filtering. Ardbeg Uigedael has no age indication for the expression consists of various malts of different ages. Malts are used partly matured in ex-bourbon and partly on ex-sherry casks."))
                .body("[1].'available'", equalTo(true))
                .body("[1].'links'.size()", equalTo(1))
                .body("[1].'links'[0].'rel'", containsString("self"))
                .body("[2].'productId'", numberMatches(3.0))
                .body("[2].'distillery'", containsString("Balvenie"))
                .body("[2].'name'", containsString("12 y.o. Doublewood"))
                .body("[2].'price'", numberMatches(5403.0))
                .body("[2].'age'", numberMatches(12.0))
                .body("[2].'volume'", numberMatches(700.0))
                .body("[2].'alcohol'", numberMatches(40.0))
                .body("[2].'description'", containsString("Has clear influences from both bourbon and sherry wood. This malt has only 12 years aged in bourbon casks and then 3 months in young Oloroso casks. The peppery character from the bourbon barrels, penetrates, as it were by the rich and full aroma of Oloroso casks it. The Balvenie Double Wood is therefore a very complex malt."))
                .body("[2].'available'", equalTo(true))
                .body("[2].'links'.size()", equalTo(1))
                .body("[2].'links'[0].'rel'", containsString("self"))
                ; // Skipping assertions on the remaining 8 elements. This limit of 3 elements can be increased in the configurations
        
        expectationHandler.expect(ems)
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(0)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(1)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(2)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(3)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(4)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(5)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(6)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(7)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(8)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(9)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")))
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(10)).keySet().containsAll(Arrays.asList("alcohol", "price", "volume")));
    }
    
    
    @Test @Timeout(60)
    public void test_3() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "_EM_468_XYZ_")
                .get(baseUrlOfSut + "/customer?name=_EM_466_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'email'", containsString("ivan.petrov@yandex.ru"))
                .body("'password'", containsString("hidden"))
                .body("'name'", containsString("Ivan Petrov"))
                .body("'phone'", containsString("+7 123 456 78 90"))
                .body("'address'", containsString("Riesstrasse 18"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_4() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/products/10")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'productId'", numberMatches(10.0))
                .body("'distillery'", containsString("Talisker"))
                .body("'name'", containsString("10 y.o."))
                .body("'price'", numberMatches(4683.0))
                .body("'age'", numberMatches(10.0))
                .body("'volume'", numberMatches(750.0))
                .body("'alcohol'", numberMatches(45.8))
                .body("'description'", containsString("Powerful peat smoke with the salinity of sea water and the moisture of fresh oysters. Full sweetness of dried fruit with smoke and strong aromas of malted barley, warm and intense. Peppery in the mouth. Big, long, warming and peppery on the finish with an attractive sweetness."))
                .body("'available'", equalTo(true));
        
    }
    
    
    @Test @Timeout(60)
    public void test_5() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("USER_ROLE", 148L)
                .d("USER_ID", "222")
                .d("ROLE_ID", "698")
            .and().insertInto("USER_ROLE", 149L)
                .d("USER_ID", "1049475")
                .d("ROLE_ID", "4928")
            .and().insertInto("PRODUCT", 150L)
                .d("NAME", "\"ILICJVQSf6Tiur0i\"")
                .d("DISTILLERY_ID", "-3290")
                .d("AGE", "1744")
                .d("ALCOHOL", "155.89544965440527")
                .d("VOLUME", "588")
                .d("PRICE", "411176.65283316746")
                .d("DESCRIPTION", "\"_EM_710_XYZ_\"")
                .d("AVAILABLE", "false")
            .and().insertInto("PRODUCT", 151L)
                .d("NAME", "\"_EM_2986_XYZ_\"")
                .d("DISTILLERY_ID", "975")
                .d("AGE", "833")
                .d("ALCOHOL", "1.684590081267052")
                .d("VOLUME", "4194590")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"_EM_2879_XYZ_\"")
                .d("AVAILABLE", "true")
            .and().insertInto("PRODUCT", 152L)
                .d("NAME", "\"fNR_zDWY0iDQnsn\"")
                .d("DISTILLERY_ID", "220")
                .d("AGE", "182")
                .d("ALCOHOL", "0.8940987593138564")
                .d("VOLUME", "-1109730602")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"_EM_2987_XYZ_\"")
                .d("AVAILABLE", "true")
            .and().insertInto("CUSTOMER_ORDER", 206L)
                .d("ID", "-67108152")
                .d("USER_ACCOUNT_ID", "0")
                .d("DATE_CREATED", "\"1998-09-16\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "-33553916")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "525085")
            .and().insertInto("CUSTOMER_ORDER", 207L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "-3263")
                .d("DATE_CREATED", "\"2010-12-05\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "700")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "-524288")
            .and().insertInto("CUSTOMER_ORDER", 208L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "-3197")
                .d("DATE_CREATED", "\"2009-06-01\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "346")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "251658855")
            .and().insertInto("CUSTOMER_ORDER", 209L)
                .d("ID", "0")
                .d("USER_ACCOUNT_ID", "-15411")
                .d("DATE_CREATED", "\"2000-06-16\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "8454851")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "-7258")
            .and().insertInto("USER_ACCOUNT", 927L)
                .d("EMAIL", "\"zzh7Cb75RZviSa\"")
                .d("PASSWORD", "\"S2CiNy0ELnBIP_\"")
                .d("NAME", "\"y6e\"")
                .d("ACTIVE", "false")
            .and().insertInto("USER_ACCOUNT", 928L)
                .d("EMAIL", "\"J0f\"")
                .d("PASSWORD", "\"BO8a4gNtcnu\"")
                .d("NAME", "\"pQ_XpXt\"")
                .d("ACTIVE", "true")
            .and().insertInto("USER_ACCOUNT", 929L)
                .d("EMAIL", "\"eOGWTi\"")
                .d("PASSWORD", "\"NgX5SrV_}2L11\"")
                .d("NAME", "\"LqPUOIbC0Ew\"")
                .d("ACTIVE", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/customer/orders/1")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'userAccount'", containsString("ivan.petrov@yandex.ru"))
                .body("'billNumber'", numberMatches(2.7132054E8))
                .body("'dateCreated'", containsString("2019-12-27T03:00:00.000+00:00"))
                .body("'productsCost'", numberMatches(8127.0))
                .body("'deliveryCost'", numberMatches(400.0))
                .body("'deliveryIncluded'", equalTo(true))
                .body("'totalCost'", numberMatches(8527.0))
                .body("'payed'", equalTo(true))
                .body("'executed'", equalTo(false));
        
    }
    
    
    @Test @Timeout(60)
    public void test_6() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "_EM_463_XYZ_")
                .get(baseUrlOfSut + "/customer/orders?EMextraParam123=_EM_462_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("size()", equalTo(1))
                .body("[0].'userAccount'", containsString("ivan.petrov@yandex.ru"))
                .body("[0].'billNumber'", numberMatches(2.7132054E8))
                .body("[0].'dateCreated'", containsString("2019-12-27T03:00:00.000+00:00"))
                .body("[0].'productsCost'", numberMatches(8127.0))
                .body("[0].'deliveryCost'", numberMatches(400.0))
                .body("[0].'deliveryIncluded'", equalTo(true))
                .body("[0].'totalCost'", numberMatches(8527.0))
                .body("[0].'payed'", equalTo(true))
                .body("[0].'executed'", equalTo(false))
                .body("[0].'links'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(0)).keySet().containsAll(Arrays.asList()));
    }
    
    
    @Test @Timeout(60)
    public void test_7() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("PRODUCT", 7022L)
                .d("NAME", "NULL")
                .d("DISTILLERY_ID", "NULL")
                .d("AGE", "698")
                .d("ALCOHOL", "0.7633375456427524")
                .d("VOLUME", "542")
                .d("PRICE", "0.03192882400129038")
                .d("DESCRIPTION", "\"wwzytv\"")
                .d("AVAILABLE", "false")
            .and().insertInto("CART", 7023L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CUSTOMER_ORDER", 7024L)
                .d("ID", "619")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "NULL")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "824")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "542")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .delete(baseUrlOfSut + "/customer/cart?name=wIpyt45unkKjoULR")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'user'", containsString("ivan.petrov@yandex.ru"))
                .body("'totalItems'", numberMatches(0.0))
                .body("'productsCost'", numberMatches(0.0))
                .body("'deliveryCost'", numberMatches(400.0))
                .body("'deliveryIncluded'", equalTo(true))
                .body("'totalCost'", numberMatches(400.0))
                .body("'cartItems'.size()", equalTo(0))
                .body("'empty'", equalTo(true));
        
    }
    
    
    @Test @Timeout(60)
    public void test_8() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/cart?name=_EM_452_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'user'", containsString("ivan.petrov@yandex.ru"))
                .body("'totalItems'", numberMatches(1.0))
                .body("'productsCost'", numberMatches(6517.0))
                .body("'deliveryCost'", numberMatches(400.0))
                .body("'deliveryIncluded'", equalTo(true))
                .body("'totalCost'", numberMatches(6917.0))
                .body("'cartItems'.size()", equalTo(1))
                .body("'cartItems'[0].'productId'", numberMatches(5.0))
                .body("'cartItems'[0].'quantity'", numberMatches(1.0))
                .body("'empty'", equalTo(false));
        
    }
    
    
    @Test @Timeout(60)
    public void test_9() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("PRODUCT", 7022L)
                .d("NAME", "NULL")
                .d("DISTILLERY_ID", "NULL")
                .d("AGE", "698")
                .d("ALCOHOL", "0.7633375456427524")
                .d("VOLUME", "542")
                .d("PRICE", "0.03192882400129038")
                .d("DESCRIPTION", "\"wwzytv\"")
                .d("AVAILABLE", "false")
            .and().insertInto("CART", 7023L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CUSTOMER_ORDER", 7024L)
                .d("ID", "619")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "NULL")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "824")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "542")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .put(baseUrlOfSut + "/customer/cart/delivery?included=false")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/hal+json")
                .body("'user'", containsString("ivan.petrov@yandex.ru"))
                .body("'totalItems'", numberMatches(1.0))
                .body("'productsCost'", numberMatches(6517.0))
                .body("'deliveryCost'", numberMatches(400.0))
                .body("'deliveryIncluded'", equalTo(false))
                .body("'totalCost'", numberMatches(6917.0))
                .body("'cartItems'.size()", equalTo(1))
                .body("'cartItems'[0].'productId'", numberMatches(5.0))
                .body("'cartItems'[0].'quantity'", numberMatches(1.0))
                .body("'empty'", equalTo(false));
        
    }


}
