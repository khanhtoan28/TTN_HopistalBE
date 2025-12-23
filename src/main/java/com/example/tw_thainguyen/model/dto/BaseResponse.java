package com.example.tw_thainguyen.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base Response DTO cho tất cả API responses
 *
 * @param <T> Data type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String error;

    /**
     * Tạo success response với data
     */
    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    /**
     * Tạo success response với data và message
     */
    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Tạo error response
     */
    public static <T> BaseResponse<T> error(String error) {
        return BaseResponse.<T>builder()
                .success(false)
                .error(error)
                .build();
    }

    /**
     * Tạo error response với message
     */
    public static <T> BaseResponse<T> error(String message, String error) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .build();
    }
}

