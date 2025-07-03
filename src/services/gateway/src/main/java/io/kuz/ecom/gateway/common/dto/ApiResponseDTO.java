package io.kuz.ecom.gateway.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

    private Boolean isSuccess;
    private T data;
    private String message;

    public ApiResponseDTO(String message, T data, Boolean isSuccess) {
        this.message = message;
        this.data = data;
        this.isSuccess = isSuccess;
    }

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(null, data, true);
    }

    public static <T> ApiResponseDTO<T> failure(String message) {
        return new ApiResponseDTO<>(message, null, false);
    }
}