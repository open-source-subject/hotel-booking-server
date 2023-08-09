package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ForbiddenException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    public ForbiddenException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.FORBIDDEN;
    }

    public ForbiddenException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}