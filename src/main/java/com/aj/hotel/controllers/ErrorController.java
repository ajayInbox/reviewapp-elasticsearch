package com.aj.hotel.controllers;

import com.aj.hotel.domain.dtos.ErrorDto;
import com.aj.hotel.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    // handle review not found exception
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorDto> reviewNotFoundExceptionHandler(ReviewNotFoundException ex){
        log.error("Caught ReviewNotFoundException", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Review not found")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    // handle review not allowed exception
    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<ErrorDto> reviewNotAllowedExceptionHandler(ReviewNotAllowedException ex){
        log.error("Caught ReviewNotAllowedException", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Review not allowed to create or update")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // handle hotel not found exception
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorDto> hotelNotFoundExceptionHandler(HotelNotFoundException ex){
        log.error("Caught HotelNotFoundException", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Specific hotel not found")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    // handle storage exception
    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorDto> storageExceptionHandler(StorageException ex){
        log.error("Caught StorageException", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Unable save or retrieve images")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle base exception
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> baseExceptionHandler(BaseException ex){
        log.error("Caught BaseException", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle all exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> storageErrorHandler(Exception ex){
        log.error("Caught Exception", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("Caught MethodArgumentNotValidException", ex);

        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream().map(
                        error -> error.getField() +":"+ error.getDefaultMessage()
                ).collect(Collectors.joining(","));

        ErrorDto errorDto = ErrorDto.builder()
                .message(errorMsg)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
