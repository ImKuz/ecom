package io.kuz.ecom.gateway.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpConfirmVerificationRequestDTO {
    private String sessionId;
    private String code;
}
