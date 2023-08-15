package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.BookingCreateDTO;
import com.bookinghotel.dto.BookingFilterDTO;
import com.bookinghotel.dto.BookingUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.security.CurrentUserLogin;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class BookingController {

    private final BookingService bookingService;

    @Tags({@Tag(name = "booking-controller-admin"), @Tag(name = "booking-controller")})
    @Operation(summary = "API get booking by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @GetMapping(UrlConstant.Booking.GET_BOOKING)
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
        return VsResponseUtil.ok(bookingService.getBookingById(bookingId));
    }

    @Operation(summary = "API get booking for user")
    @AuthorizationInfo(role = {RoleConstant.USER})
    @GetMapping(UrlConstant.Booking.GET_BOOKINGS_FOR_USER)
    public ResponseEntity<?> getBookingsForUser(@Valid @ParameterObject PaginationSortRequestDTO pagination,
                                                @Parameter(name = "principal", hidden = true)
                                                @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.getBookingsForUser(pagination, principal));
    }

    @Tag(name = "booking-controller-admin")
    @Operation(summary = "API get booking for admin")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Booking.GET_BOOKINGS_FOR_ADMIN)
    public ResponseEntity<?> getBookingsForAdmin(@Valid @ParameterObject PaginationSortRequestDTO pagination,
                                                 @ParameterObject BookingFilterDTO bookingFilterDTO) {
        return VsResponseUtil.ok(bookingService.getBookingsForAdmin(pagination, bookingFilterDTO));
    }

    @Operation(summary = "API create booking")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @PostMapping(value = UrlConstant.Booking.CREATE_BOOKING)
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingCreateDTO bookingCreateDTO,
                                           @Parameter(name = "principal", hidden = true)
                                           @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.createBooking(bookingCreateDTO, principal));
    }

    @Operation(summary = "API update booking")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(value = UrlConstant.Booking.UPDATE_BOOKING)
    public ResponseEntity<?> updateBooking(@PathVariable Long bookingId,
                                           @Valid @RequestBody BookingUpdateDTO bookingUpdateDTO,
                                           @Parameter(name = "principal", hidden = true)
                                           @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.updateBooking(bookingId, bookingUpdateDTO, principal));
    }

    @Tag(name = "booking-controller-admin")
    @Operation(summary = "API checkin booking")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(value = UrlConstant.Booking.CHECK_IN_BOOKING)
    public ResponseEntity<?> checkIn(@PathVariable Long bookingId,
                                     @Parameter(name = "principal", hidden = true)
                                     @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.checkIn(bookingId, principal));
    }

    @Tag(name = "booking-controller-admin")
    @Operation(summary = "API checkout and payment booking")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(value = UrlConstant.Booking.CHECK_OUT_PAYMENT)
    public ResponseEntity<?> checkOutAndPayment(@PathVariable Long bookingId,
                                                @Parameter(name = "principal", hidden = true)
                                                @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.checkOutAndPayment(bookingId, principal));
    }

    @Tags({@Tag(name = "booking-controller-admin"), @Tag(name = "booking-controller")})
    @Operation(summary = "API cancel booking")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @PostMapping(value = UrlConstant.Booking.CANCEL_BOOKING)
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId,
                                           @RequestParam String note,
                                           @Parameter(name = "principal", hidden = true)
                                           @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(bookingService.cancelBooking(bookingId, note, principal));
    }

}


