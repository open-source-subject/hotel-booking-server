package com.bookinghotel.repository.projection;

import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import org.springframework.beans.factory.annotation.Value;

public interface RoomRatingProjection {

    Long getId();

    Integer getStar();

    String getComment();

    String getRoomId();

    @Value("#{new com.bookinghotel.dto.common.CreatedByDTO(target.createdById, target.createdByFirstName, target.createdByLastName, target.createdByAvatar)}")
    CreatedByDTO getCreatedBy();

    @Value("#{new com.bookinghotel.dto.common.LastModifiedByDTO(target.lastModifiedById, target.lastModifiedByFirstName, target.lastModifiedByLastName, target.lastModifiedByAvatar)}")
    LastModifiedByDTO getLastModifiedBy();

}
