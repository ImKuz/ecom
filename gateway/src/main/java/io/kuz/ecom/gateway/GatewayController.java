package io.kuz.ecom.gateway;

import io.kuz.ecom.common.dto.ProductDTO;
import io.kuz.ecom.gateway.common.response.ApiResponse;
import io.kuz.ecom.gateway.mapper.ProductMapper;
import io.kuz.ecom.proto.product.GetProductByIdRequest;
import io.kuz.ecom.proto.product.GetProductByIdResponse;
import io.kuz.ecom.proto.product.ProductServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
