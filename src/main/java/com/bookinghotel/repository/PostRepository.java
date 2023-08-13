package com.bookinghotel.repository;

import com.bookinghotel.entity.Post;
import com.bookinghotel.repository.projection.PostProjection;
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
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.id = ?1 AND p.deleteFlag = false")
    Optional<Post> findById(Long postId);

    @Query("SELECT p FROM Post p WHERE p.id = ?1 AND p.deleteFlag = true")
    Optional<Post> findByIdAndIsDeleteFlag(Long postId);

    @Query(value = "SELECT p.id, p.title, p.content, p.created_date AS createdDate, p.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM posts p " +
        "LEFT JOIN users createdBy ON p.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON p.last_modified_by = lastModifiedBy.id " +
        "WHERE p.delete_flag = 0 AND p.id = ?1",
        nativeQuery = true)
    PostProjection findPostById(Long postId);

    @Query(value = "SELECT p.id, p.title, p.content, p.created_date AS createdDate, p.last_modified_date AS lastModifiedDate, " +
        "createdBy.id AS createdById, " +
        "createdBy.first_name AS createdByFirstName, " +
        "createdBy.last_name AS createdByLastName, " +
        "createdBy.avatar AS createdByAvatar, " +
        "lastModifiedBy.id AS lastModifiedById, " +
        "lastModifiedBy.first_name AS lastModifiedByFirstName, " +
        "lastModifiedBy.last_name AS lastModifiedByLastName, " +
        "lastModifiedBy.avatar AS lastModifiedByAvatar " +
        "FROM posts p " +
        "LEFT JOIN users createdBy ON p.created_by = createdBy.id " +
        "LEFT JOIN users lastModifiedBy ON p.last_modified_by = lastModifiedBy.id " +
        "WHERE p.delete_flag = :deleteFlag",
        countQuery = "SELECT COUNT(*) FROM posts p " +
            "LEFT JOIN users createdBy ON p.created_by = createdBy.id " +
            "LEFT JOIN users lastModifiedBy ON p.last_modified_by = lastModifiedBy.id " +
            "WHERE p.delete_flag = :deleteFlag",
        nativeQuery = true)
    Page<PostProjection> findAllPost(@Param("deleteFlag") Boolean deleteFlag, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM posts WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2", nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
