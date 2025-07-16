package io.kuz.ecom.gateway.product;

import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import io.kuz.ecom.gateway.product.domain.ProductSearchInput;
import io.kuz.ecom.gateway.product.dto.ProductDTO;
import io.kuz.ecom.gateway.product.dto.ProductListDTO;
import io.kuz.ecom.gateway.product.grpc.ProductGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductGrpcClient client;

    @Autowired
    public ProductController(ProductGrpcClient client) {
        this.client = client;
    }

    @GetMapping("/products/{id}")
    public ApiResponseDTO<ProductDTO> findProductById(
        @PathVariable("id") long id
    ) {
        return ApiResponseDTO.success(
            client.findProductById(id)
        );
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
        var input = ProductSearchInput
            .builder()
            .query(query)
            .categoryId(categoryId)
            .attributeOptions(attributeOptions)
            .minPriceCents(minPriceCents)
            .maxPriceCents(maxPriceCents)
            .page(page)
            .pageSize(pageSize)
            .build();

        return ApiResponseDTO.success(
            client.searchProducts(input)
        );
    }
}