package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingStatisticDTO {

    private Long totalBookingCheckin;

    private Long totalBookingCheckout;

    private Long totalBookingPending;

    private Long totalBookingCancel;

}
