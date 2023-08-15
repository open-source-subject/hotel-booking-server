package com.bookinghotel.repository;

import com.bookinghotel.entity.BookingRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookingRoomDetailRepository extends JpaRepository<BookingRoomDetail, Long> {

    @Query("SELECT brd FROM BookingRoomDetail brd WHERE brd.booking.id = ?1")
    Set<BookingRoomDetail> findAllByBookingId(Long id);

    @Query("SELECT brd FROM BookingRoomDetail brd WHERE brd.id = ?1")
    Optional<BookingRoomDetail> findById(Long id);

}
