package io.kuz.ecom.gateway.product.mapper;

import io.kuz.ecom.gateway.common.mapper.PaginationMetaMapper;
import io.kuz.ecom.gateway.product.dto.ProductListDTO;
import io.kuz.ecom.proto.product.GetProductListByCriteriaResponse;
import io.kuz.ecom.proto.product.Product;
import io.kuz.ecom.gateway.product.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDto(Product proto) {
        return new ProductDTO(
                (int) proto.getId(),
                proto.getTitle(),
                (int) proto.getCategoryId(),
                categoryLabelForCode(proto.getCategoryCode()),
                proto.getAttributesList()
                            .stream()
                            .map(ProductAttributeMapper::toDto)
                            .collect(Collectors.toList()),
                BigDecimal.valueOf(proto.getPriceCents(), 2)
        );
    }

    public static ProductListDTO toDTO(GetProductListByCriteriaResponse proto) {
        return new ProductListDTO(
            proto
                .getProductsList()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList()),
            PaginationMetaMapper.toDTO(proto.getMeta())
        );
    }

    private static String categoryLabelForCode(String code) {
        return code;
    }
}