package io.kuz.ecom.gateway.domain.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInitiateVerificationRequestDTO {
    private AuthProvider provider;
    private String identifier;
}