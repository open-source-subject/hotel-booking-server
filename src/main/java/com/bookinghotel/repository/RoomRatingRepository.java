package com.bookinghotel.repository;

import com.bookinghotel.entity.RoomRating;
import com.bookinghotel.repository.projection.RoomRatingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRatingRepository extends JpaRepository<RoomRating, Long> {

    @Query("SELECT r FROM RoomRating r WHERE r.id = ?1 AND r.deleteFlag = false")
    Optional<RoomRating> findById(Long id);

    @Query(value = "SELECT r.id, r.star, r.comment, r.room_id AS roomId, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM room_ratings r " +
        "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
        "WHERE r.id = ?1 AND r.delete_flag = 0",
        nativeQuery = true)
    RoomRatingProjection findRoomRatingById(Long id);

    @Query(value = "SELECT r.id, r.star, r.comment, r.room_id AS roomId, " +
        "r.created_date AS createdDate, r.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM room_ratings r " +
        "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
        "WHERE r.room_id = :roomId AND (:star IS NULL OR r.star = :star) AND r.delete_flag = 0",
        countQuery = "SELECT COUNT(*) FROM room_ratings r " +
            "LEFT JOIN users createdBy ON r.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON r.last_modified_by = lastModifiedBy.id " +
            "WHERE r.room_id = :roomId AND (:star IS NULL OR r.star = :star) AND r.delete_flag = 0",
        nativeQuery = true)
    Page<RoomRatingProjection> findRoomRatingsByRoomId(Pageable pageable, @Param("star") Integer starRating, @Param("roomId") Long roomId);

    @Query("SELECT r FROM RoomRating r WHERE r.room.id = ?1 and r.deleteFlag = false ")
    List<RoomRating> findAllByRoomId(Pageable pageable, Long roomId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM room_ratings WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2",
        nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
