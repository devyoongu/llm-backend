package com.llm.backend.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> { // 이후 확장될 수 있음
    private Result code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> ok(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .code(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> ok(T data) {
        return ok(data, null);
    }

    public static CommonResponse fail(String message) {
        return CommonResponse.builder()
                .code(Result.FAIL)
                .message(message)
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
