
package market.rest;

import market.domain.Cart;
import market.domain.Order;
import market.dto.CartDTO;
import market.dto.CartItemDTO;
import market.dto.CreditCardDTO;
import market.dto.OrderDTO;
import market.dto.assembler.CartDtoAssembler;
import market.dto.assembler.OrderDtoAssembler;
import market.exception.EmptyCartException;
import market.exception.UnknownEntityException;
import market.properties.MarketProperties;
import market.service.CartService;
import market.service.OrderService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "customer/cart")
@ExposesResourceFor(CartDTO.class)
@Secured({"ROLE_USER"})
public class CartRestController {
    public static final String DELIVERY = "/delivery";
    public static final String PAY = "/pay";

    private final CartService cartService;
    private final OrderService orderService;
    private final CartDtoAssembler cartDtoAssembler;
    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();
    private final MarketProperties marketProperties;

    public CartRestController(CartService cartService, OrderService orderService, MarketProperties marketProperties) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.marketProperties = marketProperties;
        cartDtoAssembler = new CartDtoAssembler(this.marketProperties);
    }

    @GetMapping
    public CartDTO getCart(Principal principal) {
        String login = principal.getName();
        Cart cart = cartService.getCartOrCreate(login);
        return toDto(cart);
    }

    @PutMapping
    public CartDTO addItem(Principal principal, @RequestBody @Valid CartItemDTO item) {
        String login = principal.getName();
        Cart cart = cartService.addToCart(login, item.getProductId(), item.getQuantity());
        return toDto(cart);
    }

    @DeleteMapping
    public CartDTO clearCart(Principal principal) {
        String login = principal.getName();
        Cart cart = cartService.clearCart(login);
        return toDto(cart);
    }

    @PutMapping(value = DELIVERY)
    public CartDTO setDelivery(Principal principal, @RequestParam(name = "included") boolean included) {
        String login = principal.getName();
        Cart cart = cartService.setDelivery(login, included);
        return toDto(cart);
    }

    @PostMapping(value = PAY)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDTO> payByCard(Principal principal, @RequestBody @Valid CreditCardDTO card) {
        String login = principal.getName();
        Order order = orderService.createUserOrder(login, marketProperties.getDeliveryCost(), card.getCcNumber());
        OrderDTO dto = orderDtoAssembler.toModel(order);

        HttpHeaders headers = new HttpHeaders();
        dto.getLink("self").ifPresent(link -> headers.setLocation(URI.create(link.getHref())));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }

    private CartDTO toDto(Cart cart) {
        CartDTO dto = cartDtoAssembler.toModel(cart);
        dto.add(linkTo(ContactsRestController.class).withRel("Customer contacts"));
        dto.add(linkTo(getClass()).slash(CartRestController.PAY).withRel("Proceed to payment"));
        for (CartItemDTO cartItemDto : dto.getCartItems())
            cartItemDto.add(linkTo(ProductsRestController.class).slash(cartItemDto.getProductId()).withRel("View product"));
        return dto;
    }
}



package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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

public class v1_gpt35_run03_CartRestControllerTest {

    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetCart() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
    }

    @Test
    public void testAddItem() {
        given()
            .when()
            .contentType("application/json")
            .body("{\"productId\":1,\"quantity\":1}")
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
    }

    @Test
    public void testClearCart() {
        given()
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
    }

    @Test
    public void testSetDelivery() {
        given()
            .when()
            .contentType("application/json")
            .queryParam("included", true)
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200);
    }

    @Test
    public void testPayByCard() {
        given()
            .when()
            .contentType("application/json")
            .body("{\"ccNumber\":\"1234567812345678\"}")
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201);
    }
}
