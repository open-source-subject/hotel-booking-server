package com.bookinghotel.dto;

import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.DateAuditingDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceDTO extends DateAuditingDTO {

    private Long id;

    private String title;

    private String thumbnail;

    private Long price;

    private String description;

    private CreatedByDTO createdBy;

    private LastModifiedByDTO lastModifiedBy;

}
