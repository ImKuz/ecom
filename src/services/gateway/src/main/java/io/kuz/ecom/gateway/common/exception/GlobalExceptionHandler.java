package io.kuz.ecom.gateway.common.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
            .status(500)
            .body(ApiResponseDTO.failure("Unexpected error: " + ex.getMessage()));
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGrpcErrors(StatusRuntimeException exception) {
        Status.Code code = exception.getStatus().getCode();

        HttpStatus httpStatus = switch (code) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        String message = Optional
                .ofNullable(exception.getMessage())
                .orElse("Something went wrong");

        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponseDTO.failure(message));
    }

    @ExceptionHandler(InputException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleIOErrors(InputException ex) {
        return ResponseEntity
            .status(400)
            .body(ApiResponseDTO.failure("Invalid argument: " + ex.getMessage()));
    }
}
