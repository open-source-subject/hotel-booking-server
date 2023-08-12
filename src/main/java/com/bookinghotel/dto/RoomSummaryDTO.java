package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomSummaryDTO {

    private Long id;

    private String name;

    private Long price;

    private String type;

    private String bed;

    private Integer size;

    private Integer capacity;

    private String services;

    private String description;

}
