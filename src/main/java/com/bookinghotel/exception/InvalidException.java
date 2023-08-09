package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    public InvalidException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public InvalidException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}