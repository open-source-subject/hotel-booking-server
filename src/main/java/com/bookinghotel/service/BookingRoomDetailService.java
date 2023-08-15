package com.bookinghotel.service;

import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingRoomDetail;

import java.util.List;
import java.util.Set;

public interface BookingRoomDetailService {

    Set<BookingRoomDetail> getBookingRoomDetailsByBooking(Long bookingId);

    Set<BookingRoomDetail> createBookingRoomDetails(Booking booking, List<Long> roomIds);

}
