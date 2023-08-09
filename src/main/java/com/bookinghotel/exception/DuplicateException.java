package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    public DuplicateException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public DuplicateException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}