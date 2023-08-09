package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InternalServerException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    public InternalServerException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public InternalServerException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}