package com.bookinghotel.dto;

import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.DateAuditingDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomAvailableDTO extends DateAuditingDTO {

    private Long id;

    private String name;

    private Long price;

    private String type;

    private String bed;

    private Integer size;

    private Integer capacity;

    private String services;

    private String description;

    private SaleSummaryDTO sale;

    private Boolean isAvailable;

    private CreatedByDTO createdBy;

    private LastModifiedByDTO lastModifiedBy;

    private List<MediaDTO> medias;

}
