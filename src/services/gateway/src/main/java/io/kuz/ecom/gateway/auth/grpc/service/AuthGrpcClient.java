package io.kuz.ecom.gateway.auth.grpc.service;

import com.google.protobuf.Empty;
import io.kuz.ecom.gateway.auth.dto.*;
import io.kuz.ecom.gateway.auth.grpc.mapper.AuthGRPCMapper;
import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import io.kuz.ecom.proto.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthGrpcClient {

    private final AuthServiceGrpc.AuthServiceBlockingStub authStab;

    @Autowired
    public AuthGrpcClient(AuthServiceGrpc.AuthServiceBlockingStub authStab) {
        this.authStab = authStab;
    }

    public SignUpInitiateVerificationResponseDTO signUpInitiateVerification(
        SignUpInitiateVerificationRequestDTO request
    ) {
        SignUpInitiateVerificationRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        SignUpInitiateVerificationResponse response = authStab.signUpInitiateVerification(grpcRequest);
        return AuthGRPCMapper.toDto(response);
    }

    public void signUpResendCode(SignUpResendCodeRequestDTO request) {
        SignUpResendCodeRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        Empty empty = authStab.signUpResendCode(grpcRequest);
    }

    public void signUpConfirmVerification(
        SignUpConfirmVerificationRequestDTO request
    ) {
        SignUpConfirmVerificationRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        Empty empty = authStab.signUpConfirmVerification(grpcRequest);
    }

    public AuthSessionResponseDTO emailSignUp(EmailSignUpRequestDTO request) {
        EmailSignUpRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        AuthSessionResponse response = authStab.emailSignUp(grpcRequest);
        return AuthGRPCMapper.toDto(response);
    }

    public AuthSessionResponseDTO emailLogin(EmailLoginRequestDTO body) {
        EmailLoginRequest request = AuthGRPCMapper.toGrpc(body);
        AuthSessionResponse response = authStab.emailLogin(request);
        return AuthGRPCMapper.toDto(response);
    }

    public void logout(LogoutRequestDTO request) {
        LogoutRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        Empty empty = authStab.logout(grpcRequest);
    }

    public ValidateTokenResponseDTO validateToken(ValidateTokenRequestDTO request) {
        ValidateTokenRequest grpcRequest = AuthGRPCMapper.toGrpc(request);
        ValidateTokenResponse response = authStab.validateToken(grpcRequest);
        return AuthGRPCMapper.toDto(response);
    }
}
