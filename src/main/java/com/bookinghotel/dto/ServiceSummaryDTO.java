package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSummaryDTO {

    private Long id;

    private String title;

    private String thumbnail;

    private Long price;

}
