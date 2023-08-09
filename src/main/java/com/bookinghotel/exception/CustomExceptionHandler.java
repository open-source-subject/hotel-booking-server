package com.bookinghotel.exception;

import com.bookinghotel.base.RestData;
import com.bookinghotel.base.VsResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2(topic = "ErrorLogger")
@RestControllerAdvice
public class CustomExceptionHandler {

    //Error validate for param
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> result = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            String errorMessage = error.getMessage();
            result.put(fieldName, errorMessage);
        });
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
    }

    //Error validate for body
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidException(BindException ex, WebRequest req) {
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            result.put(fieldName, errorMessage);
        });
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestData<?>> handlerInternalServerError(Exception ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    //Exception custom
    @ExceptionHandler(VsException.class)
    public ResponseEntity<RestData<?>> handleVsException(VsException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getErrMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<RestData<?>> handleDuplicateException(DuplicateException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<RestData<?>> handleAccessDeniedException(ForbiddenException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<RestData<?>> handlerInternalServerException(InternalServerException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<RestData<?>> handlerInvalidException(InvalidException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestData<?>> handlerNotFoundException(NotFoundException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<RestData<?>> handleUnauthorizedException(UnauthorizedException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<RestData<?>> handleUploadImageException(UploadFileException ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        return VsResponseUtil.error(ex.getStatusCode(), ex.getMessage());
    }

}