package io.kuz.ecom.gateway.product;

import io.kuz.ecom.gateway.common.response.ApiResponse;
import io.kuz.ecom.gateway.product.dto.ProductDTO;
import io.kuz.ecom.gateway.product.dto.ProductListDTO;
import io.kuz.ecom.gateway.product.mapper.ProductFetchCriteriaMapper;
import io.kuz.ecom.gateway.product.mapper.ProductMapper;
import io.kuz.ecom.proto.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductServiceGrpc.ProductServiceBlockingStub productStub;

    @Autowired
    public ProductController(ProductServiceGrpc.ProductServiceBlockingStub productStub) {
        this.productStub = productStub;
    }

    @GetMapping("/products/{id}")
    public ApiResponse<ProductDTO> findProductById(
        @PathVariable("id") long id
    ) {
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