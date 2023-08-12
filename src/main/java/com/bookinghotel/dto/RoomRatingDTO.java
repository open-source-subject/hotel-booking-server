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
@Setter
@Getter
public class RoomRatingDTO extends DateAuditingDTO {

    private Long id;

    private Integer star;

    private String comment;

    private CreatedByDTO createdBy;

    private LastModifiedByDTO lastModifiedBy;

    private Long roomId;

}
