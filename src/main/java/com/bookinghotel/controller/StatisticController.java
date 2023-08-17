package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.RevenueRequestDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class StatisticController {

    private final StatisticService statisticService;

    @Operation(summary = "API statistic room booked for month")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Statistic.STATISTIC_ROOM_BOOKED_MONTH)
    public ResponseEntity<?> statisticRoomBookedForMonth(@Valid @ParameterObject PaginationSearchSortRequestDTO pagination,
                                                         @RequestParam Integer month, @RequestParam Integer year) {
        return VsResponseUtil.ok(statisticService.statisticRoomBookedForMonth(pagination, month, year));
    }

    @Operation(summary = "API statistic customer top booking")
    @GetMapping(UrlConstant.Statistic.STATISTIC_CUSTOMER_TOP_BOOKING)
    public ResponseEntity<?> statisticCustomerTopBooking() {
        return VsResponseUtil.ok(statisticService.statisticCustomerTopBooking());
    }

    @Operation(summary = "API statistic revenue follow month and year")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Statistic.STATISTIC_REVENUE)
    public ResponseEntity<?> statisticRevenue(@Valid @ParameterObject RevenueRequestDTO revenueRequestDTO) {
        return VsResponseUtil.ok(statisticService.statisticRevenue(revenueRequestDTO));
    }

    @Operation(summary = "API statistic booking by status")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Statistic.STATISTIC_BOOKING_BY_STATUS)
    public ResponseEntity<?> statisticBookingForMonth(@RequestParam Integer month, @RequestParam Integer year) {
        return VsResponseUtil.ok(statisticService.statisticBookingForMonth(month, year));
    }

}
