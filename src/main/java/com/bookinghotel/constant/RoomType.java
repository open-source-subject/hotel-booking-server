package com.bookinghotel.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RoomType {

    STANDARD("Standard"),
    SUPERIOR("Superior"),
    DELUXE("Deluxe"),
    SUITE("Suite");

    private String value;

}
