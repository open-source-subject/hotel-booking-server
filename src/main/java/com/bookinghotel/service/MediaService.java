package com.bookinghotel.service;

import com.bookinghotel.dto.MediaDetailDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationRequestDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Post;
import com.bookinghotel.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MediaService {

    PaginationResponseDTO<MediaDetailDTO> getMediasInTrash(PaginationRequestDTO requestDTO, Boolean deleteFlag);

    CommonResponseDTO deleteMediaPermanently(Long mediaId);

    CommonResponseDTO restoreMedia(Long mediaId);

    void deleteMediaByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

    List<Media> getMediaByRoom(Long roomId);

    List<Media> getMediaByRoomAndIsDeleteFlag(Long roomId);

    Set<Media> createMediasForRoom(Room room, List<MultipartFile> files);

    //Delete media if not found MediaDTO in RoomUpdateDTO
    Room deleteMediaFromRoomUpdate(Room room, RoomUpdateDTO roomUpdateDTO);

    List<Media> getMediaByPost(Long postId);

    List<Media> getMediaByPostAndIsDeleteFlag(Long postId);

    Set<Media> createMediasForPost(Post post, List<MultipartFile> files);

    //Delete media if not found MediaDTO in PostUpdateDTO
    Post deleteMediaFromPostUpdate(Post post, PostUpdateDTO postUpdateDTO);

}
