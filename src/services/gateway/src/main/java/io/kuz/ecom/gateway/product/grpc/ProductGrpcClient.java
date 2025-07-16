package io.kuz.ecom.gateway.product.grpc;

import io.kuz.ecom.gateway.product.domain.ProductSearchInput;
import io.kuz.ecom.gateway.product.dto.ProductDTO;
import io.kuz.ecom.gateway.product.dto.ProductListDTO;
import io.kuz.ecom.gateway.product.mapper.ProductFetchCriteriaMapper;
import io.kuz.ecom.gateway.product.mapper.ProductMapper;
import io.kuz.ecom.proto.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGrpcClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub productStub;

    @Autowired
    public ProductGrpcClient(ProductServiceGrpc.ProductServiceBlockingStub productStub) {
        this.productStub = productStub;
    }

    public ProductDTO findProductById(long id) {
        GetProductByIdRequest request = GetProductByIdRequest
            .newBuilder()
            .setId(id)
            .build();

        GetProductByIdResponse response = productStub.getProductById(request);
        return ProductMapper.toDto(response.getProduct());
    }

    public ProductListDTO searchProducts(ProductSearchInput input) {
        // TODO: Handle exception
        var criteria = ProductFetchCriteriaMapper.mapFromInput(input);

        var request = GetProductListByCriteriaRequest
            .newBuilder()
            .setCriteria(criteria)
            .setPage(input.getPage())
            .setPageSize(input.getPageSize())
            .build();

        GetProductListByCriteriaResponse response = productStub.getProductListByCriteria(request);
        return ProductMapper.toDTO(response);
    }
}
