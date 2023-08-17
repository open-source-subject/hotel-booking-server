package com.bookinghotel.service;

import com.bookinghotel.dto.BookingStatisticDTO;
import com.bookinghotel.dto.RevenueMonthDTO;
import com.bookinghotel.dto.RevenueRequestDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;

import java.util.List;
import java.util.Map;

public interface StatisticService {

    PaginationResponseDTO<Map<String, Object>> statisticRoomBookedForMonth(PaginationSearchSortRequestDTO request, Integer month, Integer year);

    List<Map<String, Object>> statisticCustomerTopBooking();

    List<RevenueMonthDTO> statisticRevenue(RevenueRequestDTO revenueRequestDTO);

    BookingStatisticDTO statisticBookingForMonth(Integer month, Integer year);

}
