package com.bookinghotel.repository;

import com.bookinghotel.entity.Media;
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
import java.util.Set;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    @Query(value = "SELECT * FROM medias m WHERE m.delete_flag = :deleteFlag",
            countQuery = "SELECT COUNT(*) FROM medias m WHERE m.delete_flag = :deleteFlag",
            nativeQuery = true)
    Page<Media> findAllMedia(@Param("deleteFlag") Boolean deleteFlag, Pageable pageable);

    @Query(value = "SELECT m FROM Media m WHERE m.id = ?1 AND m.deleteFlag = true")
    Optional<Media> findMediaByIdAndIsDeleteFlag(Long id);

    //room
    @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
    List<Media> findByRoomId(Long roomId);

    @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = true")
    List<Media> findByRoomIdAndIsDeleteFlag(Long roomId);

    @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
    Set<Media> findByRoomToSet(Long roomId);

    @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.id NOT IN ?2 AND m.deleteFlag = false")
    List<Media> findByRoomIdAndNotInMedia(Long roomId, List<Long> mediaIds);

    //post
    @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.deleteFlag = false")
    List<Media> findByPostId(Long postId);

    @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.deleteFlag = true")
    List<Media> findByPostIdAndIsDeleteFlag(Long postId);

    @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.deleteFlag = false")
    Set<Media> findByPostToSet(Long postId);

    @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.id NOT IN ?2 AND m.deleteFlag = false")
    List<Media> findByPostIdAndNotInMedia(Long postId, List<Long> mediaIds);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM medias WHERE delete_flag = ?1 AND DATEDIFF(NOW(), last_modified_date) >= ?2", nativeQuery = true)
    void deleteByDeleteFlag(boolean isDelete, int daysToDelete);

}
