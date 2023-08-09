package com.bookinghotel.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VsResponseUtil {

    public static ResponseEntity<RestData<?>> ok(Object data) {
        return ok(HttpStatus.OK, data);
    }

    public static ResponseEntity<RestData<?>> ok(HttpStatus status, Object data) {
        RestData<?> response = new RestData<>(data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<RestData<?>> error(HttpStatus status, Object message) {
        RestData<?> response = RestData.error(message);
        return new ResponseEntity<>(response, status);
    }
}
