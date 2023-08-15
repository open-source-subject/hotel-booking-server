package com.bookinghotel.service;

import com.bookinghotel.dto.*;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingRoomDetail;
import com.bookinghotel.entity.BookingServiceDetail;
import com.bookinghotel.repository.projection.BookingProjection;
import com.bookinghotel.security.UserPrincipal;

import java.util.List;
import java.util.Set;

public interface BookingService {

    BookingDTO getBookingById(Long bookingId);

    PaginationResponseDTO<BookingDTO> getBookingsForUser(PaginationSortRequestDTO requestDTO, UserPrincipal principal);

    PaginationResponseDTO<BookingDTO> getBookingsForAdmin(PaginationSortRequestDTO requestDTO, BookingFilterDTO bookingFilterDTO);

    BookingDTO createBooking(BookingCreateDTO bookingCreateDTO, UserPrincipal principal);

    BookingDTO updateBooking(Long bookingId, BookingUpdateDTO bookingUpdateDTO, UserPrincipal principal);

    BookingDTO checkIn(Long bookingId, UserPrincipal principal);

    BookingDTO checkOutAndPayment(Long bookingId, UserPrincipal principal);

    CommonResponseDTO cancelBooking(Long bookingId, String note, UserPrincipal principal);

    void lockUserRefuseToCheckIn();

    List<BookingDTO> mapperToBookingDTOs(List<BookingProjection> bookings);

    Long calculateTotalRoomPrice(BookingProjection booking, Set<BookingRoomDetail> bookingRoomDetails);

    Long calculateTotalRoomPrice(Booking booking, Set<BookingRoomDetail> bookingRoomDetails);

    Long calculateTotalServicePrice(BookingProjection booking, Set<BookingServiceDetail> bookingServiceDetails);

    Long calculateTotalServicePrice(Booking booking, Set<BookingServiceDetail> bookingServiceDetails);

    List<BookingSurchargeDTO> calculateSurcharge(BookingProjection booking, Set<BookingRoomDetail> bookingRoomDetails);

    List<BookingSurchargeDTO> calculateSurcharge(Booking booking, Set<BookingRoomDetail> bookingRoomDetails);

}
