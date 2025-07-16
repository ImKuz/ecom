package io.kuz.ecom.gateway.auth.grpc.mapper;

import io.kuz.ecom.gateway.auth.dto.*;
import io.kuz.ecom.gateway.auth.dto.AuthProvider;
import io.kuz.ecom.proto.auth.*;

public class AuthGRPCMapper {

    public static SignUpInitiateVerificationRequest toGrpc(SignUpInitiateVerificationRequestDTO dto) {
        return SignUpInitiateVerificationRequest.newBuilder()
            .setProviderValue(dto.getProvider().ordinal())
            .setIdentifier(dto.getIdentifier())
            .build();
    }

    public static SignUpInitiateVerificationResponseDTO toDto(SignUpInitiateVerificationResponse proto) {
        return new SignUpInitiateVerificationResponseDTO(
            proto.getSessionId(),
            proto.getRetryTimeout(),
            proto.getTtl()
        );
    }

    public static SignUpResendCodeRequest toGrpc(SignUpResendCodeRequestDTO dto) {
        return SignUpResendCodeRequest.newBuilder()
            .setSessionId(dto.getSessionId())
            .build();
    }

    public static SignUpConfirmVerificationRequest toGrpc(SignUpConfirmVerificationRequestDTO dto) {
        return SignUpConfirmVerificationRequest.newBuilder()
            .setSessionId(dto.getSessionId())
            .setCode(dto.getCode())
            .build();
    }

    public static AuthSessionResponseDTO toDto(AuthSessionResponse proto) {
        return new AuthSessionResponseDTO(
            proto.getUserId(),
            proto.getAccessToken(),
            proto.getRefreshToken()
        );
    }

    public static EmailSignUpRequest toGrpc(EmailSignUpRequestDTO dto) {
        return EmailSignUpRequest.newBuilder()
            .setSessionId(dto.getSessionId())
            .setPassword(dto.getPassword())
            .build();
    }

    public static EmailLoginRequest toGrpc(EmailLoginRequestDTO dto) {
        return EmailLoginRequest.newBuilder()
            .setEmail(dto.getEmail())
            .setPassword(dto.getPassword())
            .build();
    }

    public static LogoutRequest toGrpc(LogoutRequestDTO dto) {
        return LogoutRequest.newBuilder()
            .setAccessToken(dto.getAccessToken())
            .build();
    }

    public static ValidateTokenRequest toGrpc(ValidateTokenRequestDTO dto) {
        return ValidateTokenRequest.newBuilder()
            .setToken(dto.getToken())
            .build();
    }

    public static SignUpInitiateVerificationRequestDTO toDto(SignUpInitiateVerificationRequest proto) {
        return new SignUpInitiateVerificationRequestDTO(
            AuthProvider.values()[proto.getProviderValue()],
            proto.getIdentifier()
        );
    }

    public static SignUpResendCodeRequestDTO toDto(SignUpResendCodeRequest proto) {
        return new SignUpResendCodeRequestDTO(proto.getSessionId());
    }

    public static SignUpConfirmVerificationRequestDTO toDto(SignUpConfirmVerificationRequest proto) {
        return new SignUpConfirmVerificationRequestDTO(proto.getSessionId(), proto.getCode());
    }

    public static EmailSignUpRequestDTO toDto(EmailSignUpRequest proto) {
        return new EmailSignUpRequestDTO(proto.getSessionId(), proto.getPassword());
    }

    public static EmailLoginRequestDTO toDto(EmailLoginRequest proto) {
        return new EmailLoginRequestDTO(proto.getEmail(), proto.getPassword());
    }

    public static LogoutRequestDTO toDto(LogoutRequest proto) {
        return new LogoutRequestDTO(proto.getAccessToken());
    }

    public static ValidateTokenRequestDTO toDto(ValidateTokenRequest proto) {
        return new ValidateTokenRequestDTO(proto.getToken());
    }

    public static io.kuz.ecom.proto.auth.AuthProvider toGrpc(AuthProvider provider) {
        return io.kuz.ecom.proto.auth.AuthProvider.valueOf(provider.name());
    }

    public static AuthProvider toDto(io.kuz.ecom.proto.auth.AuthProvider provider) {
        return AuthProvider.valueOf(provider.name());
    }

    public static ValidateTokenResponseDTO toDto(ValidateTokenResponse proto) {
        return new ValidateTokenResponseDTO(proto.getUserId());
    }
}
