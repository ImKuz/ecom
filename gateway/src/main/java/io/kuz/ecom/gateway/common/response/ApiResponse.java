package io.kuz.ecom.gateway.common.response;

public class ApiResponse<T> {

    private Boolean isSuccess;
    private T data;
    private String message;

    public ApiResponse() {}

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}