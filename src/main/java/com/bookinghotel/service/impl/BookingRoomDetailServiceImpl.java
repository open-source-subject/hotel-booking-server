package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingRoomDetail;
import com.bookinghotel.entity.Room;
import com.bookinghotel.entity.Sale;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.exception.VsException;
import com.bookinghotel.repository.BookingRoomDetailRepository;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.service.BookingRoomDetailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class BookingRoomDetailServiceImpl implements BookingRoomDetailService {

    private final BookingRoomDetailRepository bookingRoomDetailRepository;

    private final RoomRepository roomRepository;

    @Override
    public Set<BookingRoomDetail> getBookingRoomDetailsByBooking(Long bookingId) {
        return bookingRoomDetailRepository.findAllByBookingId(bookingId);
    }

    @Override
    public Set<BookingRoomDetail> createBookingRoomDetails(Booking booking, List<Long> roomIds) {
        List<Room> bookingRooms = new LinkedList<>();
        for (Long id : roomIds) {
            Optional<Room> room = roomRepository.findById(id);
            checkNotFoundRoomById(room, id);
            bookingRooms.add(room.get());
        }
        checkRoomAvailable(bookingRooms, booking.getExpectedCheckIn(), booking.getExpectedCheckOut());
        Set<BookingRoomDetail> bookingRoomDetails = new LinkedHashSet<>();
        LocalDateTime now = LocalDateTime.now();
        if (bookingRooms.size() > 0) {
            for (Room room : bookingRooms) {
                BookingRoomDetail bookingRoomDetail = new BookingRoomDetail(booking, room);
                bookingRoomDetail.setPrice(room.getPrice());
                Sale sale = room.getSale();
                if (ObjectUtils.isNotEmpty(sale) && sale.getDeleteFlag().equals(CommonConstant.FALSE)
                        && sale.getDayStart().isBefore(now) && now.isAfter(sale.getDayEnd())) {
                    bookingRoomDetail.setSalePercent(sale.getSalePercent());
                }
                bookingRoomDetailRepository.save(bookingRoomDetail);
                bookingRoomDetails.add(bookingRoomDetail);
            }
        } else {
            throw new InvalidException(ErrorMessage.Booking.ERR_BOOKING_NOT_ROOM);
        }
        return bookingRoomDetails;
    }

    private void checkRoomAvailable(List<Room> rooms, LocalDateTime checkin, LocalDateTime checkout) {
        List<Room> roomUnavailable = roomRepository.findAllUnavailable(checkin, checkout);
        Map<String, String> result = new HashMap<>();
        for (Room room : rooms) {
            for (Room roomUn : roomUnavailable) {
                if (room.getId().equals(roomUn.getId())) {
                    result.put(room.getName(), ErrorMessage.Room.ERR_ROOM_UNAVAILABLE);
                }
            }
        }
        if (result.size() > 0) {
            throw new VsException(HttpStatus.BAD_REQUEST, result);
        }
    }

    private void checkNotFoundRoomById(Optional<Room> room, Long roomId) {
        if (room.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID, roomId));
        }
    }

}
