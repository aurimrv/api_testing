
package market.rest;

import market.dto.OrderDTO;
import market.dto.assembler.OrderDtoAssembler;
import market.exception.UnknownEntityException;
import market.service.OrderService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Customer orders history.
 */
@RestController
@RequestMapping(value = "customer/orders")
@ExposesResourceFor(OrderDTO.class)
@Secured({"ROLE_USER"})
public class OrdersRestController {
    private final OrderService orderService;
    private final OrderDtoAssembler orderDtoAssembler = new OrderDtoAssembler();

    public OrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * View orders.
     *
     * @return orders list of the specified customer
     */
    @GetMapping
    public List<OrderDTO> getOrders(Principal principal) {
        return orderService.getUserOrders(principal.getName()).stream()
            .map(orderDtoAssembler::toModel)
            .collect(toList());
    }

    /**
     * View a single order.
     *
     * @return order of the specified customer
     * @throws UnknownEntityException if the order with the specified id doesn't exist
     */
    @GetMapping(value = "/{orderId}")
    public OrderDTO getOrder(Principal principal, @PathVariable long orderId) {
        String login = principal.getName();
        return orderService.getUserOrder(login, orderId)
            .map(orderDtoAssembler::toModel)
            .orElseThrow(() -> new UnknownEntityException(OrderDTO.class, orderId));
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

public class run01_OrdersRestControllerTest {

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
    public void testErrorScenarios() {
        // Test error scenarios that force the API to return 5xx status codes
        // Add assertions to verify the responses
    }

    @Test
    public void testSchemaValidation() {
        // Test schema validation for API responses
        // Ensure conformity with OpenAPI schema
        // Check for required fields, data types, and valid values
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Test business rule enforcement for operations like POST, PUT, DELETE
        // Validate correct creation, modification, or deletion of resources
        // Capture and assert any violations
    }
}
