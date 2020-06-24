package com.dasan.aaserver.exception.handle;

import com.dasan.aaserver.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
        log.error(e.getMessage(), e.getStackTrace());
        ApiError apiError = new ApiError(e.getStatus(), e.getMessage());
        return this.buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
