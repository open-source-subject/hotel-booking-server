package com.bookinghotel.service;

import com.bookinghotel.dto.BookingServiceDTO;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingServiceDetail;

import java.util.List;
import java.util.Set;

public interface BookingServiceDetailService {

    Set<BookingServiceDetail> getBookingServiceDetailsByBooking(Long bookingId);

    Set<BookingServiceDetail> createBookingServiceDetails(Booking booking, List<BookingServiceDTO> bookingService);

}
