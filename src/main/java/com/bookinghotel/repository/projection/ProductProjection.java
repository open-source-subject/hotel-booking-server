package com.bookinghotel.repository.projection;

import com.bookinghotel.dto.ServiceSummaryDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ProductProjection {

    Long getId();

    String getName();

    String getThumbnail();

    String getDescription();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    @Value("#{new com.bookinghotel.dto.ServiceSummaryDTO(target.serviceId, target.serviceTitle, target.serviceThumbnail, target.servicePrice)}")
    ServiceSummaryDTO getService();

    @Value("#{new com.bookinghotel.dto.common.CreatedByDTO(target.createdById, target.createdByFirstName, target.createdByLastName, target.createdByAvatar)}")
    CreatedByDTO getCreatedBy();

    @Value("#{new com.bookinghotel.dto.common.LastModifiedByDTO(target.lastModifiedById, target.lastModifiedByFirstName, target.lastModifiedByLastName, target.lastModifiedByAvatar)}")
    LastModifiedByDTO getLastModifiedBy();

}
