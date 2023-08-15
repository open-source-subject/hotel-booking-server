package com.bookinghotel.repository;

import com.bookinghotel.entity.Service;
import com.bookinghotel.repository.projection.ServiceProjection;
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
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT s FROM Service s WHERE s.id = ?1 AND s.deleteFlag = false")
    Optional<Service> findById(Long id);

    @Query("SELECT s FROM Service s WHERE s.id = ?1 AND s.deleteFlag = true")
    Optional<Service> findByIdAndIsDeleteFlag(Long id);

    @Query("SELECT s FROM Service s WHERE s.deleteFlag = false")
    List<Service> findAllForChatBot();

    @Query(value = "SELECT s.id, s.title, s.thumbnail, s.price, " +
        "s.description, s.created_date AS createdDate, s.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM services s " +
        "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
        "WHERE s.id = ?1 AND s.delete_flag = 0",
        nativeQuery = true)
    ServiceProjection findServiceById(Long id);

    @Query(value = "SELECT s.id, s.title, s.thumbnail, s.price, " +
        "s.description, s.created_date AS createdDate, s.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM services s " +
        "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
        "WHERE s.delete_flag = :deleteFlag " +
        "AND ( " +
        "COALESCE(:keyword, '') = '' " +
        "OR s.title LIKE CONCAT('%', :keyword, '%') " +
        "OR s.price LIKE CONCAT('%', :keyword, '%') " +
        ")",
        countQuery = "SELECT COUNT(*) FROM services s " +
            "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
            "WHERE s.delete_flag = :deleteFlag " +
            "AND ( " +
            "COALESCE(:keyword, '') = '' " +
            "OR s.title LIKE CONCAT('%', :keyword, '%') " +
            "OR s.price LIKE CONCAT('%', :keyword, '%') " +
            ")",
        nativeQuery = true)
    Page<ServiceProjection> findAllService(@Param("keyword") String keyword, @Param("deleteFlag") Boolean deleteFlag, Pageable pageable);

    @Query("SELECT s FROM Service s WHERE s.id IN ?1 AND s.deleteFlag = false")
    List<Service> findAllByIds(List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM services WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2", nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
