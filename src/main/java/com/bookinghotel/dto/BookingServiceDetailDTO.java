package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingServiceDetailDTO {

    private ServiceSummaryDTO service;

    private Integer amount;

}
