package com.bookinghotel.repository;

import com.bookinghotel.dto.BookingFilterDTO;
import com.bookinghotel.dto.RevenueRequestDTO;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.repository.projection.BookingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.id = ?1")
    Optional<Booking> findById(Long id);

    @Query(value = "SELECT b.id, b.expected_check_in AS expectedCheckIn, b.expected_check_out AS expectedCheckOut, " +
        "b.check_in AS checkIn, b.check_out AS checkOut, b.status, b.note,  " +
        "b.created_date AS createdDate, b.last_modified_date AS lastModifiedDate, " +
        "u.id AS userId, u.email AS userEmail, u.phone_number AS userPhoneNumber, " +
        "u.first_name AS userFirstName, u.last_name AS userLastName, u.avatar AS userAvatar, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM bookings b " +
        "LEFT JOIN users createdBy ON b.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON b.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN users u ON u.id = b.user_id " +
        "WHERE b.id = ?1",
        nativeQuery = true)
    BookingProjection findBookingProjectionById(Long id);

    @Query(value = "SELECT b.* FROM bookings b " +
        "INNER JOIN users u ON u.id = b.user_id " +
        "INNER JOIN roles r ON r.id = u.role_id " +
        "WHERE r.role_name ='ROLE_USER' AND b.status = :status ",
        nativeQuery = true)
    List<Booking> findBookingUserByStatus(@Param("status") String bookingStatus);

    @Query(value = "SELECT COUNT(*) FROM bookings b " +
        "WHERE (:month IS NULL OR MONTH(b.expected_check_in) = :month) " +
        "AND YEAR(b.expected_check_out) = :year " +
        "AND b.status = :status",
        countQuery = "SELECT COUNT(*) FROM bookings b " +
            "WHERE (:month IS NULL OR MONTH(b.expected_check_in) = :month) " +
            "AND YEAR(b.expected_check_out) = :year " +
            "AND b.status = :status",
        nativeQuery = true)
    Long countBookingByStatus(@Param("month") Integer month, @Param("year") Integer year, @Param("status") String bookingStatus);

    @Query(value = "SELECT b.id, b.expected_check_in AS expectedCheckIn, b.expected_check_out AS expectedCheckOut, " +
        "b.check_in AS checkIn, b.check_out AS checkOut, b.status, b.note,  " +
        "b.created_date AS createdDate, b.last_modified_date AS lastModifiedDate, " +
        "u.id AS userId, u.email AS userEmail, u.phone_number AS userPhoneNumber, " +
        "u.first_name AS userFirstName, u.last_name AS userLastName, u.avatar AS userAvatar, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM bookings b " +
        "LEFT JOIN users createdBy ON b.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON b.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN users u ON u.id = b.user_id " +
        "WHERE b.user_id = :userId",
        countQuery = "SELECT COUNT(*) FROM bookings b " +
            "LEFT JOIN users createdBy ON b.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON b.last_modified_by = lastModifiedBy.id " +
            "LEFT JOIN users u ON u.id = b.user_id " +
            "WHERE b.user_id = :userId",
        nativeQuery = true)
    Page<BookingProjection> findAllForUser(@Param("userId") String userId, Pageable pageable);

    @Query(value = "SELECT b.id, b.expected_check_in AS expectedCheckIn, b.expected_check_out AS expectedCheckOut, " +
        "b.check_in AS checkIn, b.check_out AS checkOut, b.status, b.note,  " +
        "b.created_date AS createdDate, b.last_modified_date AS lastModifiedDate, " +
        "u.id AS userId, u.email AS userEmail, u.phone_number AS userPhoneNumber, " +
        "u.first_name AS userFirstName, u.last_name AS userLastName, u.avatar AS userAvatar, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM bookings b " +
        "LEFT JOIN users createdBy ON b.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON b.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN users u ON u.id = b.user_id " +
        "WHERE (:#{#filter.fromDate} IS NULL OR b.expected_check_in >= :#{#filter.fromDate}) " +
        "AND (:#{#filter.toDate} IS NULL OR b.expected_check_out <= :#{#filter.toDate}) " +
        "AND (:status IS NULL OR b.status = :status)",
        countQuery = "SELECT COUNT(*) FROM bookings b " +
            "WHERE (:#{#filter.fromDate} IS NULL OR b.expected_check_in >= :#{#filter.fromDate}) " +
            "AND (:#{#filter.toDate} IS NULL OR b.expected_check_out <= :#{#filter.toDate}) " +
            "AND (:status IS NULL OR b.status = :status)",
        nativeQuery = true)
    Page<BookingProjection> findAllForAdmin(@Param("filter") BookingFilterDTO filter, @Param("status") String bookingStatus, Pageable pageable);

    @Query(value = "SELECT * FROM bookings b " +
        "WHERE MONTH(b.created_date) BETWEEN :#{#filter.fromMonth} AND :#{#filter.toMonth} " +
        "AND YEAR(b.created_date) = :#{#filter.year} " +
        "AND b.status = 'CHECKED_OUT'", nativeQuery = true)
    List<Booking> statisticRevenue(@Param("filter") RevenueRequestDTO filter);

}
