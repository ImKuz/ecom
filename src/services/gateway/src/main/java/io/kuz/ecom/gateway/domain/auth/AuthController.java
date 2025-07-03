package io.kuz.ecom.gateway.domain.auth;

import io.kuz.ecom.gateway.domain.auth.dto.*;
import io.kuz.ecom.gateway.domain.auth.mapper.AuthMapper;
import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
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
    public ApiResponseDTO<SignUpInitiateVerificationResponseDTO> signUpInitiateVerification(
        @RequestBody SignUpInitiateVerificationRequestDTO body
    ) {
        SignUpInitiateVerificationRequest request = AuthMapper.toGrpc(body);
        SignUpInitiateVerificationResponse response = authStab.signUpInitiateVerification(request);
        return ApiResponseDTO.success(AuthMapper.toDto(response));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/verify/resend")
    public ApiResponseDTO<Void> signUpResendCode(
        @RequestBody SignUpResendCodeRequestDTO body
    ) {
        SignUpResendCodeRequest request = AuthMapper.toGrpc(body);
        authStab.signUpResendCode(request);
        return ApiResponseDTO.success(null);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/verify/confirm")
    public ApiResponseDTO<Void> signUpConfirmVerification(
        @RequestBody SignUpConfirmVerificationRequestDTO body
    ) {
        SignUpConfirmVerificationRequest request = AuthMapper.toGrpc(body);
        authStab.signUpConfirmVerification(request);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/email/sign-up")
    public ApiResponseDTO<AuthSessionResponseDTO> emailSignUp(
        @RequestBody EmailSignUpRequestDTO body
    ) {
        EmailSignUpRequest request = AuthMapper.toGrpc(body);
        AuthSessionResponse response = authStab.emailSignUp(request);
        AuthSessionResponseDTO dto = AuthMapper.toDto(response);
        return ApiResponseDTO.success(dto);
    }

    @PostMapping("/email/login")
    public ApiResponseDTO<AuthSessionResponseDTO> emailLogin(
        @RequestBody EmailLoginRequestDTO body
    ) {
        EmailLoginRequest request = AuthMapper.toGrpc(body);
        AuthSessionResponse response = authStab.emailLogin(request);
        AuthSessionResponseDTO dto = AuthMapper.toDto(response);
        return ApiResponseDTO.success(dto);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostMapping("/logout")
    public ApiResponseDTO<Void> logout(
        @RequestBody LogoutRequestDTO body
    ) {
        LogoutRequest request = AuthMapper.toGrpc(body);
        authStab.logout(request);
        return ApiResponseDTO.success(null);
    }
}
