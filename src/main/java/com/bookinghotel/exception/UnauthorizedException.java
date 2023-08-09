package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    private Integer statusCodeValue;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.UNAUTHORIZED;
    }

    public UnauthorizedException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}