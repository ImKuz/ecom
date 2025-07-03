package io.kuz.ecom.gateway.domain.product;

import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import io.kuz.ecom.gateway.domain.product.dto.ProductDTO;
import io.kuz.ecom.gateway.domain.product.dto.ProductListDTO;
import io.kuz.ecom.gateway.domain.product.mapper.ProductFetchCriteriaMapper;
import io.kuz.ecom.gateway.domain.product.mapper.ProductMapper;
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
    public ApiResponseDTO<ProductDTO> findProductById(
        @PathVariable("id") long id
    ) {
        GetProductByIdRequest request = GetProductByIdRequest
            .newBuilder()
            .setId(id)
            .build();

        GetProductByIdResponse response = productStub.getProductById(request);

        ProductDTO dto = ProductMapper.toDto(response.getProduct());

        return ApiResponseDTO.success(dto);
    }

    @GetMapping("/products/search")
    public ApiResponseDTO<ProductListDTO> searchProducts(
        @RequestParam(required = false, name = "query") String query,
        @RequestParam(required = false, name = "category_id") Long categoryId,
        @RequestParam(required = false, name = "attribute_options") String attributeOptions,
        @RequestParam(required = false, name = "min_price_cents") Long minPriceCents,
        @RequestParam(required = false, name = "max_price_cents") Long maxPriceCents,
        @RequestParam(required = false, name = "page") Integer page,
        @RequestParam(required = false, name = "page_size") Integer pageSize
    ) {
        ProductFetchCriteria criteria = ProductFetchCriteriaMapper.mapFromQuery(
            query, categoryId, attributeOptions, minPriceCents, maxPriceCents
        );

        GetProductListByCriteriaRequest request = GetProductListByCriteriaRequest
            .newBuilder()
            .setCriteria(criteria)
            .setPage(page)
            .setPageSize(pageSize)
            .build();

        GetProductListByCriteriaResponse response = productStub.getProductListByCriteria(request);

        return ApiResponseDTO.success(
            ProductMapper.toDTO(response)
        );
    }
}