package io.kuz.ecom.gateway.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateTokenResponseDTO {
    private String userId;
}
