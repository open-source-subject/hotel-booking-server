package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private String message;

    private HttpStatus statusCode;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.statusCode = httpStatus;
    }

}