package com.bookinghotel.repository;

import com.bookinghotel.entity.BookingServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookingServiceDetailRepository extends JpaRepository<BookingServiceDetail, Long> {

    @Query("SELECT bsd FROM BookingServiceDetail bsd WHERE bsd.booking.id = ?1")
    Set<BookingServiceDetail> findAllByBookingId(Long id);

    @Query("SELECT bsd FROM BookingServiceDetail bsd WHERE bsd.id = ?1")
    Optional<BookingServiceDetail> findById(Long id);

}
