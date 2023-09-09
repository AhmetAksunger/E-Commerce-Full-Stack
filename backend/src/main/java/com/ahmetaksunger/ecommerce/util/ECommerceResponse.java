package com.ahmetaksunger.ecommerce.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class ECommerceResponse<T> {

    public ECommerceResponse(@NonNull T response, @NonNull HttpStatus httpStatus) {
        this.response = response;
        this.httpStatus = httpStatus;
        this.isSuccess = httpStatus.is2xxSuccessful();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    private HttpStatus httpStatus;

    @Builder.Default
    private Long time = System.currentTimeMillis();

    private Boolean isSuccess;

    public static final ECommerceResponse<Void> SUCCESS = ECommerceResponse.<Void>builder()
            .isSuccess(true)
            .httpStatus(HttpStatus.OK)
            .build();

    public static <T> ECommerceResponse<T> successOf(T response) {
        return ECommerceResponse.<T>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ECommerceResponse<T> createdOf(T response) {
        return ECommerceResponse.<T>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public static <T> ECommerceResponse<T> badRequestOf(T response) {
        return ECommerceResponse.<T>builder()
                .response(response)
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
