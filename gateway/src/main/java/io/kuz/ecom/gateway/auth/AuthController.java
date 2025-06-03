package io.kuz.ecom.gateway.auth;

import io.kuz.ecom.gateway.auth.dto.*;
import io.kuz.ecom.gateway.auth.mapper.AuthMapper;
import io.kuz.ecom.gateway.common.response.ApiResponse;
import io.kuz.ecom.proto.auth.AuthServiceGrpc;
import io.kuz.ecom.proto.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceGrpc.AuthServiceBlockingStub authStab;

    @Autowired
    public AuthController(AuthServiceGrpc.AuthServiceBlockingStub authStab) {
        this.authStab = authStab;
    }

    @PostMapping("/verify/start")
    public ApiResponse<SignUpInitiateVerificationResponseDTO> signUpInitiateVerification(
        @RequestBody SignUpInitiateVerificationRequestDTO body
    ) {
        SignUpInitiateVerificationRequest request = AuthMapper.toGrpc(body);
        SignUpInitiateVerificationResponse response = authStab.signUpInitiateVerification(request);
        return ApiResponse.success(AuthMapper.toDto(response));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/verify/resend")
    public ApiResponse<Void> signUpResendCode(
        @RequestBody SignUpResendCodeRequestDTO body
    ) {
        SignUpResendCodeRequest request = AuthMapper.toGrpc(body);
        authStab.signUpResendCode(request);
        return ApiResponse.success(null);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/verify/confirm")
    public ApiResponse<Void> signUpConfirmVerification(
        @RequestBody SignUpConfirmVerificationRequestDTO body
    ) {
        SignUpConfirmVerificationRequest request = AuthMapper.toGrpc(body);
        authStab.signUpConfirmVerification(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/email/sign-up")
    public ApiResponse<AuthSessionResponseDTO> emailSignUp(
        @RequestBody EmailSignUpRequestDTO body
    ) {
        EmailSignUpRequest request = AuthMapper.toGrpc(body);
        AuthSessionResponse response = authStab.emailSignUp(request);
        AuthSessionResponseDTO dto = AuthMapper.toDto(response);
        return ApiResponse.success(dto);
    }

    @PostMapping("/email/login")
    public ApiResponse<AuthSessionResponseDTO> emailLogin(
        @RequestBody EmailLoginRequestDTO body
    ) {
        EmailLoginRequest request = AuthMapper.toGrpc(body);
        AuthSessionResponse response = authStab.emailLogin(request);
        AuthSessionResponseDTO dto = AuthMapper.toDto(response);
        return ApiResponse.success(dto);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(
        @RequestBody LogoutRequestDTO body
    ) {
        LogoutRequest request = AuthMapper.toGrpc(body);
        authStab.logout(request);
        return ApiResponse.success(null);
    }
}
