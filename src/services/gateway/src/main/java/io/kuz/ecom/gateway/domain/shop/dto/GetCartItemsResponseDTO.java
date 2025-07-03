package io.kuz.ecom.gateway.domain.shop.dto;

import io.kuz.ecom.gateway.common.dto.PaginationMetaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItemsResponseDTO {
    private List<CartItemDTO> items;
    private PaginationMetaDTO meta;
}