package io.kuz.ecom.gateway;

import io.kuz.ecom.gateway.dto.product.ProductDTO;
import io.kuz.ecom.gateway.common.exception.InputException;
import io.kuz.ecom.gateway.common.response.ApiResponse;
import io.kuz.ecom.gateway.dto.product.ProductListDTO;
import io.kuz.ecom.gateway.mapper.ProductFetchCriteriaMapper;
import io.kuz.ecom.gateway.mapper.ProductMapper;
import io.kuz.ecom.proto.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
public class GatewayController {

    private final ProductServiceGrpc.ProductServiceBlockingStub productStub;

    @Autowired
    public GatewayController(ProductServiceGrpc.ProductServiceBlockingStub productStub) {
        this.productStub = productStub;
    }

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello!");
    }

    // PRODUCT

    @GetMapping("/products/{id}")
    public ApiResponse<ProductDTO> findProductById(@PathVariable("id") long id) {
        GetProductByIdRequest request = GetProductByIdRequest
            .newBuilder()
            .setId(id)
            .build();

        GetProductByIdResponse response = productStub.getProductById(request);

        ProductDTO dto = ProductMapper.toDto(response.getProduct());

        return ApiResponse.success(dto);
    }

    @GetMapping("/products/search")
    public ApiResponse<ProductListDTO> searchProducts(
        @RequestParam(required = false, name = "query") String query,
        @RequestParam(required = false, name = "category_id") Long categoryId,
        @RequestParam(required = false, name = "attribute_options") String attributeOptions,
        @RequestParam(required = false, name = "min_price_cents") Long minPriceCents,
        @RequestParam(required = false, name = "max_price_cents") Long maxPriceCents,
        @RequestParam(required = false, name = "limit") Long limit,
        @RequestParam(required = false, name = "offset") Long offset
    ) {
        ProductFetchCriteria criteria = ProductFetchCriteriaMapper.mapFromQuery(
            query, categoryId, attributeOptions, minPriceCents, maxPriceCents
        );

        GetProductListByCriteriaRequest request = GetProductListByCriteriaRequest
            .newBuilder()
            .setCriteria(criteria)
            .setLimit(limit)
            .setOffset(offset)
            .build();

        GetProductListByCriteriaResponse response = productStub.getProductListByCriteria(request);

        return ApiResponse.success(
            ProductMapper.toDTO(response)
        );
    }
}
