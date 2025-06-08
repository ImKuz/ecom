package io.kuz.ecom.gateway.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Boolean isSuccess;
    private T data;
    private String message;

    public ApiResponse(String message, T data, Boolean isSuccess) {
        this.message = message;
        this.data = data;
        this.isSuccess = isSuccess;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, data, true);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(message, null, false);
    }
}