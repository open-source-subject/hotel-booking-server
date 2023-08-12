package com.bookinghotel.repository;

import com.bookinghotel.dto.common.DateTimeFilterDTO;
import com.bookinghotel.entity.Room;
import com.bookinghotel.repository.projection.RoomProjection;
import com.bookinghotel.repository.projection.StatisticRoomBookedProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "SELECT r.* FROM rooms r WHERE r.type LIKE CONCAT('%', :typeRoom, '%') AND r.delete_flag = false GROUP BY r.type", nativeQuery = true)
    List<Room> findByTypeForChatBot(@Param("typeRoom") String typeRoom);

    @Query("SELECT r FROM Room r WHERE r.id = ?1 AND r.deleteFlag = false")
    Optional<Room> findById(Long id);

    @Query("SELECT r FROM Room r WHERE r.id = ?1 AND r.deleteFlag = true")
    Optional<Room> findByIdAndIsDeleteFlag(Long id);

    @Query(value = "SELECT r.id, r.name, r.price, r.type, r.bed, r.size, r.capacity, r.services, r.description, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, " +
        "s.id AS saleId, s.day_start AS saleDayStart, s.day_end AS saleDayEnd, s.sale_percent AS saleSalePercent, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM rooms r " +
        "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN sales s ON s.id = r.sale_id " +
        "WHERE r.id = ?1 AND r.delete_flag = 0",
        nativeQuery = true)
    RoomProjection findRoomById(Long id);

    @Query("SELECT r FROM Room r WHERE r.id IN ?1 AND r.deleteFlag = false")
    List<Room> findAllByIds(List<Long> ids);

    @Query(value = "SELECT r.id, r.name, r.price, r.type, r.bed, r.size, r.capacity, r.services, r.description, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, " +
        "s.id AS saleId, s.day_start AS saleDayStart, s.day_end AS saleDayEnd, s.sale_percent AS saleSalePercent, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM rooms r " +
        "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN sales s ON s.id = r.sale_id " +
        "WHERE (:typeRoom IS NULL OR r.type LIKE CONCAT('%', :typeRoom, '%')) " +
        "AND ( " +
        "COALESCE(:keyword, '') = '' " +
        "OR r.name LIKE CONCAT('%', :keyword, '%') " +
        "OR r.type LIKE CONCAT('%', :keyword, '%') " +
        "OR r.price LIKE CONCAT('%', :keyword, '%') " +
        ")" +
        "AND r.delete_flag = :deleteFlag",
        countQuery = "SELECT COUNT(*) FROM rooms r " +
            "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
            "LEFT JOIN sales s ON s.id = r.sale_id " +
            "WHERE (:typeRoom IS NULL OR r.type LIKE CONCAT('%', :typeRoom, '%')) " +
            "AND ( " +
            "COALESCE(:keyword, '') = '' " +
            "OR r.name LIKE CONCAT('%', :keyword, '%') " +
            "OR r.type LIKE CONCAT('%', :keyword, '%') " +
            "OR r.price LIKE CONCAT('%', :keyword, '%') " +
            ")" +
            "AND r.delete_flag = :deleteFlag",
        nativeQuery = true)
    Page<RoomProjection> findAllRoom(@Param("keyword") String keyword, @Param("typeRoom") String typeRoom,
                                     @Param("deleteFlag") Boolean deleteFlag, Pageable pageable);

    @Query(value =
        "WITH data_check_checkin AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= :#{#filter.fromDateTime} " +
            "AND b.expected_check_out >= DATE_SUB(:#{#filter.fromDateTime}, INTERVAL 2 HOUR) " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
        "), " +
        "data_check_checkout AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= DATE_ADD(:#{#filter.toDateTime}, INTERVAL 2 HOUR) " +
            "AND b.expected_check_out >= :#{#filter.toDateTime} " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
        "), " +
        "data_unavailable AS ( " +
            "SELECT * FROM data_check_checkin data_1 " +
            "UNION " +
            "SELECT * FROM data_check_checkout data_2 " +
        ") " +
        "SELECT r.id, r.name, r.price, r.type, r.bed, r.size, r.capacity, r.services, r.description, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, " +
        "CASE WHEN r.id IN (SELECT id FROM data_unavailable) THEN FALSE ELSE TRUE END AS isAvailable," +
        "s.id AS saleId, s.day_start AS saleDayStart, s.day_end AS saleDayEnd, s.sale_percent AS saleSalePercent, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM rooms r " +
        "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
        "LEFT JOIN sales s ON s.id = r.sale_id " +
        "WHERE (:capacity IS NULL OR r.capacity = :capacity) " +
        "AND (:typeRoom IS NULL OR r.type LIKE CONCAT('%', :typeRoom, '%')) " +
        "AND ( " +
        "COALESCE(:keyword, '') = '' " +
        "OR r.name LIKE CONCAT('%', :keyword, '%') " +
        "OR r.type LIKE CONCAT('%', :keyword, '%') " +
        "OR r.price LIKE CONCAT('%', :keyword, '%') " +
        ")" +
        "AND r.delete_flag = 0",
        countQuery = "WITH data_check_checkin AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= :#{#filter.fromDateTime} " +
            "AND b.expected_check_out >= DATE_SUB(:#{#filter.fromDateTime}, INTERVAL 2 HOUR) " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
            "), " +
            "data_check_checkout AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= DATE_ADD(:#{#filter.toDateTime}, INTERVAL 2 HOUR) " +
            "AND b.expected_check_out >= :#{#filter.toDateTime} " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
            "), " +
            "data_unavailable AS ( " +
            "SELECT * FROM data_check_checkin data_1 " +
            "UNION " +
            "SELECT * FROM data_check_checkout data_2 " +
            ") " +
            "SELECT COUNT(*) FROM rooms r " +
            "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
            "LEFT JOIN sales s ON s.id = r.sale_id " +
            "WHERE (:capacity IS NULL OR r.capacity = :capacity) " +
            "AND (:typeRoom IS NULL OR r.type LIKE CONCAT('%', :typeRoom, '%')) " +
            "AND ( " +
            "COALESCE(:keyword, '') = '' " +
            "OR r.name LIKE CONCAT('%', :keyword, '%') " +
            "OR r.type LIKE CONCAT('%', :keyword, '%') " +
            "OR r.price LIKE CONCAT('%', :keyword, '%') " +
            ")" +
            "AND r.delete_flag = 0",
        nativeQuery = true)
    Page<RoomProjection> findAllAvailable(@Param("keyword") String keyword, @Param("capacity") Integer capacity,
                                          @Param("typeRoom") String typeRoom, @Param("filter") DateTimeFilterDTO filter,
                                          Pageable pageable);

    @Query(value =
        "WITH data_check_checkin AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= :checkin " +
            "AND b.expected_check_out >= DATE_SUB(:checkin, INTERVAL 2 HOUR) " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
        "), " +
        "data_check_checkout AS ( " +
            "SELECT r.* FROM rooms r " +
            "INNER JOIN booking_room_details brd ON brd.room_id = r.id " +
            "INNER JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE b.expected_check_in <= DATE_ADD(:checkout, INTERVAL 2 HOUR) " +
            "AND b.expected_check_out >= :checkout " +
            "AND b.status NOT IN ('CANCEL', 'CHECKED_OUT') " +
        ") " +
        "SELECT * FROM data_check_checkin data_1 " +
        "UNION " +
        "SELECT * FROM data_check_checkout data_2",
        nativeQuery = true)
    List<Room> findAllUnavailable(@Param("checkin") LocalDateTime checkin, @Param("checkout") LocalDateTime checkout);

    @Query(value = "SELECT r.id, r.name, r.price, r.type, r.bed, r.size, r.capacity, r.services, r.description, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, COUNT(b.id) AS `value` " +
        "FROM rooms r " +
        "LEFT JOIN booking_room_details brd ON brd.room_id = r.id " +
        "LEFT JOIN bookings b ON b.id = brd.booking_id " +
        "WHERE (b.id IS NULL OR (MONTH(b.created_date) = :month AND YEAR(b.created_date) = :year)) " +
        "AND ( " +
        "COALESCE(:keyword, '') = '' " +
        "OR r.name LIKE CONCAT('%', :keyword, '%') " +
        "OR r.type LIKE CONCAT('%', :keyword, '%') " +
        "OR r.price LIKE CONCAT('%', :keyword, '%') " +
        ")" +
        "GROUP BY r.id " +
        "ORDER BY " +
        "CASE WHEN :sortType = 'ASC' THEN COUNT(b.id) END ASC, " +
        "CASE WHEN :sortType = 'DESC' THEN COUNT(b.id) END DESC",
        countQuery = "SELECT COUNT(*) FROM rooms r " +
            "LEFT JOIN booking_room_details brd ON brd.room_id = r.id " +
            "LEFT JOIN bookings b ON b.id = brd.booking_id " +
            "WHERE (b.id IS NULL OR (MONTH(b.created_date) = :month AND YEAR(b.created_date) = :year)) " +
            "AND ( " +
            "COALESCE(:keyword, '') = '' " +
            "OR r.name LIKE CONCAT('%', :keyword, '%') " +
            "OR r.type LIKE CONCAT('%', :keyword, '%') " +
            "OR r.price LIKE CONCAT('%', :keyword, '%') " +
            ") " +
            "ORDER BY " +
            "CASE WHEN :sortType = 'ASC' THEN COUNT(b.id) END ASC, " +
            "CASE WHEN :sortType = 'DESC' THEN COUNT(b.id) END DESC",
        nativeQuery = true)
    Page<StatisticRoomBookedProjection> statisticRoomBookedForMonth(@Param("month") Integer month, @Param("year") Integer year,
                                                                    @Param("keyword") String keyword, @Param("sortType") String sortType,
                                                                    Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rooms WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2",
        nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
