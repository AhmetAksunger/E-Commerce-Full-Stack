package com.ahmetaksunger.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DefaultExceptionResponse {

    private long timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
