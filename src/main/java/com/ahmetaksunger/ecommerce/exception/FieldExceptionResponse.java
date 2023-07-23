package com.ahmetaksunger.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldExceptionResponse {
    private long timeStamp;
    private int status;
    private String error;
    private HashMap<String,String> messages;
    private String path;
}
