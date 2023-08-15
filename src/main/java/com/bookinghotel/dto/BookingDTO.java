package com.bookinghotel.dto;

import com.bookinghotel.constant.BookingStatus;
import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.DateAuditingDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingDTO extends DateAuditingDTO {

    private Long id;

    @JsonFormat(pattern = CommonConstant.PATTERN_DATE_TIME)
    private LocalDateTime expectedCheckIn;

    @JsonFormat(pattern = CommonConstant.PATTERN_DATE_TIME)
    private LocalDateTime expectedCheckOut;

    @JsonFormat(pattern = CommonConstant.PATTERN_DATE_TIME)
    private LocalDateTime checkIn;

    @JsonFormat(pattern = CommonConstant.PATTERN_DATE_TIME)
    private LocalDateTime checkOut;

    private BookingStatus status;

    private String note;

    private Long totalRoomPrice;

    private List<BookingRoomDetailDTO> rooms = new LinkedList<>();

    private Long totalServicePrice;

    private List<BookingServiceDetailDTO> services = new LinkedList<>();

    private List<BookingSurchargeDTO> surcharges = new LinkedList<>();

    private UserSummaryDTO user;

    private CreatedByDTO createdBy;

    private LastModifiedByDTO lastModifiedBy;

}
