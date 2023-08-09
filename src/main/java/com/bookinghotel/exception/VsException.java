package com.bookinghotel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class VsException extends RuntimeException {

    private Object errMessage;

    private HttpStatus statusCode;

    public VsException(HttpStatus httpStatus, Object message) {
        this.errMessage = message;
        this.statusCode = httpStatus;
    }

}
