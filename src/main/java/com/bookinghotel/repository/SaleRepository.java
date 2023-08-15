package com.bookinghotel.repository;

import com.bookinghotel.entity.Sale;
import com.bookinghotel.repository.projection.SaleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.id = ?1 AND s.deleteFlag = false")
    Optional<Sale> findById(Long saleId);

    @Query("SELECT s FROM Sale s WHERE s.id = ?1 AND s.deleteFlag = true")
    Optional<Sale> findByIdAndIsDeleteFlag(Long saleId);

    @Query(value = "SELECT s.id, s.day_start AS dayStart, s.day_end AS dayEnd, " +
        "s.sale_percent AS salePercent, s.created_date AS createdDate, s.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM sales s " +
        "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
        "WHERE s.delete_flag = 0 AND s.id = ?1",
        nativeQuery = true)
    SaleProjection findSaleById(Long saleId);

    @Query(value = "SELECT s.id, s.day_start AS dayStart, s.day_end AS dayEnd, " +
        "s.sale_percent AS salePercent, s.created_date AS createdDate, s.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM sales s " +
        "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
        "WHERE (:keyword IS NULL or s.sale_percent LIKE CONCAT('%', :keyword, '%')) " +
        "AND s.delete_flag = :deleteFlag",
        countQuery = "SELECT COUNT(*) FROM sales s " +
            "LEFT JOIN users createdBy ON s.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON s.last_modified_by = lastModifiedBy.id " +
            "WHERE (:keyword IS NULL or s.sale_percent LIKE CONCAT('%', :keyword, '%')) " +
            "AND s.delete_flag = :deleteFlag",
        nativeQuery = true)
    Page<SaleProjection> findAllSale(@Param("keyword") String keyword, @Param("deleteFlag") Boolean deleteFlag, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sales WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2", nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
