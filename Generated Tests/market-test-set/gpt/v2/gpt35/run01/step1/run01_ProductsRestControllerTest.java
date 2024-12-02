
package market.rest;

import market.domain.Product;
import market.dto.ProductDTO;
import market.dto.assembler.ProductDtoAssembler;
import market.exception.UnknownEntityException;
import market.service.ProductService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "products")
@ExposesResourceFor(ProductDTO.class)
public class ProductsRestController {

    private final ProductService productService;
    private final ProductDtoAssembler productAssembler = new ProductDtoAssembler();

    public ProductsRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Collection<ProductDTO> getProducts() {
        return productService.findAll().stream()
                .sorted(Comparator.comparing(Product::getId))
                .map(productAssembler::toModel)
                .peek(this::addSelfLink)
                .collect(Collectors.toList());
    }

    private void addSelfLink(ProductDTO dto) {
        dto.add(linkTo(methodOn(getClass()).getProduct(dto.getProductId())).withRel("self");
    }

    @GetMapping(value = "/{productId}")
    public ProductDTO getProduct(@PathVariable long productId) {
        return productService.findById(productId)
                .map(productAssembler::toModel)
                .map(this::addListLink)
                .orElseThrow(() -> new UnknownEntityException(ProductDTO.class, productId));
    }

    private ProductDTO addListLink(ProductDTO dto) {
        dto.add(linkTo(methodOn(getClass()).getProducts()).withRel("All products");
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

public class run01_ProductsRestControllerTest {

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
        // Write tests to force the API to return 5xx status codes (e.g., 500 Internal Server Error)
        // Add assertions to verify the responses
    }

    @Test
    public void testSchemaValidation() {
        // Write tests to ensure API responses conform to the OpenAPI schema
        // Check for required fields, data type conformity, and valid values
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Write tests to validate business rules for operations like POST, PUT, DELETE
        // Ensure correct resource creation, modification, and deletion
    }
}
