package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<FieldExceptionResponse> handle(Exception exception, HttpServletRequest request){

        HashMap<String,String> messages = new HashMap<>();

        if(exception instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            for (FieldError fieldError:ex.getBindingResult().getFieldErrors()) {
                messages.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
        }

        var response = FieldExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(messages)
                .timeStamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<DefaultExceptionResponse> handle(UnauthorizedException exception, HttpServletRequest request){

        return new ResponseEntity<>(DefaultExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<DefaultExceptionResponse> handle(NotFoundException exception, HttpServletRequest request){
        return new ResponseEntity<>(DefaultExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidRequestParamException.class, InsufficientProductQuantityException.class})
    public ResponseEntity<DefaultExceptionResponse> handleBadRequest(Exception exception, HttpServletRequest request){
        return new ResponseEntity<>(DefaultExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserAlreadyHasCartException.class})
    public ResponseEntity<DefaultExceptionResponse> handle(UserAlreadyHasCartException exception, HttpServletRequest request){
        return new ResponseEntity<>(DefaultExceptionResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build(),HttpStatus.CONFLICT);
    }
}
