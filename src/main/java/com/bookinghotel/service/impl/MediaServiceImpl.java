package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.MediaType;
import com.bookinghotel.dto.MediaDetailDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationRequestDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Post;
import com.bookinghotel.entity.Room;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.MediaMapper;
import com.bookinghotel.repository.MediaRepository;
import com.bookinghotel.repository.PostRepository;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.service.MediaService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    private final RoomRepository roomRepository;

    private final PostRepository postRepository;

    private final MediaMapper mediaMapper;

    private final UploadFileUtil uploadFile;

    @Override
    public PaginationResponseDTO<MediaDetailDTO> getMediasInTrash(PaginationRequestDTO requestDTO, Boolean deleteFlag) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageableSortLastModifiedDate(requestDTO);
        Page<Media> medias = mediaRepository.findAllMedia(deleteFlag, pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, medias);
        return new PaginationResponseDTO<MediaDetailDTO>(meta, mediaMapper.toMediaDetailDTOs(medias.getContent()));
    }

    @Override
    public CommonResponseDTO deleteMediaPermanently(Long mediaId) {
        Optional<Media> media = mediaRepository.findMediaByIdAndIsDeleteFlag(mediaId);
        checkNotFoundMediaIsDeleteFlagById(media, mediaId);
        mediaRepository.delete(media.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO restoreMedia(Long mediaId) {
        Optional<Media> media = mediaRepository.findMediaByIdAndIsDeleteFlag(mediaId);
        checkNotFoundMediaIsDeleteFlagById(media, mediaId);
        media.get().setDeleteFlag(CommonConstant.FALSE);
        mediaRepository.save(media.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.RESTORE_SUCCESS);
    }

    @Override
    @Transactional
    public void deleteMediaByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords) {
        mediaRepository.deleteByDeleteFlag(isDeleteFlag, daysToDeleteRecords);
    }

    @Override
    public List<Media> getMediaByRoom(Long roomId) {
        return mediaRepository.findByRoomId(roomId);
    }

    @Override
    public List<Media> getMediaByRoomAndIsDeleteFlag(Long roomId) {
        return mediaRepository.findByRoomIdAndIsDeleteFlag(roomId);
    }

    @Override
    public Set<Media> createMediasForRoom(Room room, List<MultipartFile> files) {
        Set<Media> medias = new HashSet<>();
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (Objects.requireNonNull(contentType).equals("video/mp4")) {
                Media video = new Media();
                video.setUrl(uploadFile.uploadFile(file));
                video.setType(MediaType.VIDEO);
                video.setRoom(room);
                medias.add(mediaRepository.save(video));
            } else {
                Media image = new Media();
                image.setUrl(uploadFile.uploadFile(file));
                image.setType(MediaType.IMAGE);
                image.setRoom(room);
                medias.add(mediaRepository.save(image));
            }
        }
        return medias;
    }

    @Override
    public Room deleteMediaFromRoomUpdate(Room room, RoomUpdateDTO roomUpdateDTO) {
        if (CollectionUtils.isNotEmpty(roomUpdateDTO.getMediaIds())) {
            List<Media> mediaDeleteFlag = mediaRepository.findByRoomIdAndNotInMedia(room.getId(),
                    roomUpdateDTO.getMediaIds());
            if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
                mediaDeleteFlag.forEach(item -> {
                    item.setDeleteFlag(CommonConstant.TRUE);
                    mediaRepository.save(item);
                });
                roomRepository.save(room);
                room.setMedias(room.getMedias().stream().filter(item -> item.getDeleteFlag() == Boolean.FALSE).collect(Collectors.toSet()));
            }
        } else {
            if (roomUpdateDTO.getFiles() == null) {
                throw new InvalidException(ErrorMessage.Room.ERR_NO_PHOTO);
            }
        }
        return room;
    }

    @Override
    public List<Media> getMediaByPost(Long postId) {
        return mediaRepository.findByPostId(postId);
    }

    @Override
    public List<Media> getMediaByPostAndIsDeleteFlag(Long postId) {
        return mediaRepository.findByPostIdAndIsDeleteFlag(postId);
    }

    @Override
    public Set<Media> createMediasForPost(Post post, List<MultipartFile> files) {
        Set<Media> medias = new HashSet<>();
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (Objects.requireNonNull(contentType).equals("video/mp4")) {
                Media video = new Media();
                video.setUrl(uploadFile.uploadFile(file));
                video.setType(MediaType.VIDEO);
                video.setPost(post);
                medias.add(mediaRepository.save(video));
            } else {
                Media image = new Media();
                image.setUrl(uploadFile.uploadFile(file));
                image.setType(MediaType.IMAGE);
                image.setPost(post);
                medias.add(mediaRepository.save(image));
            }
        }
        return medias;
    }

    @Override
    public Post deleteMediaFromPostUpdate(Post post, PostUpdateDTO postUpdateDTO) {
        if (CollectionUtils.isNotEmpty(postUpdateDTO.getMediaIds())) {
            List<Media> mediaDeleteFlag = mediaRepository.findByPostIdAndNotInMedia(post.getId(),
                    postUpdateDTO.getMediaIds());
            if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
                mediaDeleteFlag.forEach(item -> {
                    item.setDeleteFlag(CommonConstant.TRUE);
                    mediaRepository.save(item);
                });
                postRepository.save(post);
                post.setMedias(post.getMedias().stream().filter(item -> item.getDeleteFlag() == Boolean.FALSE).collect(Collectors.toSet()));
            }
        } else {
            List<Media> mediaDeleteFlag = mediaRepository.findByPostId(post.getId());
            if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
                mediaDeleteFlag.forEach(item -> {
                    item.setDeleteFlag(CommonConstant.TRUE);
                    mediaRepository.save(item);
                });
                postRepository.save(post);
                post.setMedias(new HashSet<>());
            }
        }
        return post;
    }

    private void checkNotFoundMediaIsDeleteFlagById(Optional<Media> media, Long mediaId) {
        if (media.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Media.ERR_NOT_FOUND_ID_IN_TRASH, mediaId));
        }
    }

}
