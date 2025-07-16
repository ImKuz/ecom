package io.kuz.ecom.gateway.auth;

import io.kuz.ecom.gateway.auth.dto.*;
import io.kuz.ecom.gateway.auth.grpc.service.AuthGrpcClient;
import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthController {

    private final AuthGrpcClient client;

    @Autowired
    public AuthController(AuthGrpcClient client) {
        this.client = client;
    }

    @PostMapping("/verify/start")
    public ApiResponseDTO<SignUpInitiateVerificationResponseDTO> signUpInitiateVerification(
        @RequestBody SignUpInitiateVerificationRequestDTO body
    ) {
        return ApiResponseDTO.success(
            client.signUpInitiateVerification(body)
        );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/verify/resend")
    public ApiResponseDTO<Void> signUpResendCode(
        @RequestBody SignUpResendCodeRequestDTO body
    ) {
        client.signUpResendCode(body);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/verify/confirm")
    public ApiResponseDTO<Void> signUpConfirmVerification(
        @RequestBody SignUpConfirmVerificationRequestDTO body
    ) {
        client.signUpConfirmVerification(body);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/email/sign-up")
    public ApiResponseDTO<AuthSessionResponseDTO> emailSignUp(
        @RequestBody EmailSignUpRequestDTO body
    ) {
        return ApiResponseDTO.success(
            client.emailSignUp(body)
        );
    }

    @PostMapping("/email/login")
    public ApiResponseDTO<AuthSessionResponseDTO> emailLogin(
        @RequestBody EmailLoginRequestDTO body
    ) {
        return ApiResponseDTO.success(
            client.emailLogin(body)
        );
    }

    @PostMapping("/logout")
    public ApiResponseDTO<Void> logout(
        @RequestBody LogoutRequestDTO body
    ) {
        client.logout(body);
        return ApiResponseDTO.success(null);
    }
}
