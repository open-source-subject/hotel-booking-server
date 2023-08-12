package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.*;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.common.DateTimeFilterDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Room;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.MediaMapper;
import com.bookinghotel.mapper.RoomMapper;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.repository.projection.RoomProjection;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.MediaService;
import com.bookinghotel.service.RoomService;
import com.bookinghotel.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final MediaService mediaService;

    private final UserRepository userRepository;

    private final RoomMapper roomMapper;

    private final MediaMapper mediaMapper;

    @Override
    public RoomDTO getRoom(Long roomId) {
        RoomProjection room = roomRepository.findRoomById(roomId);
        checkNotFoundRoomById(room, roomId);
        return toRoomDTO(room);
    }

    @Override
    public PaginationResponseDTO<RoomDTO> getRooms(PaginationSearchSortRequestDTO requestDTO, String roomType, Boolean deleteFlag) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.ROOM);
        Page<RoomProjection> rooms = roomRepository.findAllRoom(requestDTO.getKeyword(), roomType, deleteFlag,
                pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.ROOM, rooms);
        return new PaginationResponseDTO<>(meta, toRoomDTOs(rooms, deleteFlag));
    }

    @Override
    public PaginationResponseDTO<RoomAvailableDTO> getRoomsAvailable(PaginationSearchSortRequestDTO requestDTO,
                                                                     RoomFilterDTO roomFilter) {
        String roomType = roomFilter.getRoomType() == null ? null : roomFilter.getRoomType().getValue();
        DateTimeFilterDTO filter = new DateTimeFilterDTO();
        filter.setFromDateTime(LocalDateTime.of(roomFilter.getCheckin(), CommonConstant.TIME_14H00));
        filter.setToDateTime(LocalDateTime.of(roomFilter.getCheckin(), CommonConstant.TIME_12H00));
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.ROOM);
        Page<RoomProjection> rooms = roomRepository.findAllAvailable(requestDTO.getKeyword(),
                roomFilter.getCapacity(), roomType, filter, pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.ROOM, rooms);
        return new PaginationResponseDTO<>(meta, toRoomAvailableDTOs(rooms));
    }

    @Override
    @Transactional
    public RoomDTO createRoom(RoomCreateDTO roomCreateDTO, UserPrincipal currentUser) {
        User creator = userRepository.getUser(currentUser);
        Room room = roomMapper.createDtoToRoom(roomCreateDTO);
        roomRepository.save(room);
        Set<Media> medias = mediaService.createMediasForRoom(room, roomCreateDTO.getFiles());
        room.setMedias(medias);
        return roomMapper.toRoomDTO(room, creator, creator);
    }

    @Override
    @Transactional
    public RoomDTO updateRoom(Long roomId, RoomUpdateDTO roomUpdateDTO, UserPrincipal currentUser) {
        Optional<Room> currentRoom = roomRepository.findById(roomId);
        checkNotFoundRoomById(currentRoom, roomId);
        roomMapper.updateRoomFromDTO(roomUpdateDTO, currentRoom.get());
        //Delete media if not found in mediaDTO
        Room roomUpdate = mediaService.deleteMediaFromRoomUpdate(currentRoom.get(), roomUpdateDTO);
        //add file if exist
        if (roomUpdateDTO.getFiles() != null) {
            Set<Media> medias = mediaService.createMediasForRoom(currentRoom.get(), roomUpdateDTO.getFiles());
            roomUpdate.getMedias().addAll(medias);
            roomRepository.save(roomUpdate);
        }
        User creator = userRepository.findById(roomUpdate.getCreatedBy()).get();
        User updater = userRepository.getUser(currentUser);
        return roomMapper.toRoomDTO(roomUpdate, creator, updater);
    }

    @Override
    @Transactional
    public CommonResponseDTO deleteRoom(Long roomId) {
        Optional<Room> currentRoom = roomRepository.findById(roomId);
        checkNotFoundRoomById(currentRoom, roomId);
        currentRoom.get().setDeleteFlag(CommonConstant.TRUE);
        roomRepository.save(currentRoom.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO deleteRoomPermanently(Long roomId) {
        Optional<Room> room = roomRepository.findByIdAndIsDeleteFlag(roomId);
        checkNotFoundRoomIsDeleteFlagById(room, roomId);
        roomRepository.delete(room.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO restoreRoom(Long roomId) {
        Optional<Room> room = roomRepository.findByIdAndIsDeleteFlag(roomId);
        checkNotFoundRoomIsDeleteFlagById(room, roomId);
        room.get().setDeleteFlag(CommonConstant.FALSE);
        roomRepository.save(room.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.RESTORE_SUCCESS);
    }

    @Override
    @Transactional
    public void deleteRoomByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords) {
        roomRepository.deleteByDeleteFlag(isDeleteFlag, daysToDeleteRecords);
    }

    private List<RoomAvailableDTO> toRoomAvailableDTOs(Page<RoomProjection> roomProjections) {
        List<RoomAvailableDTO> roomAvailableDTOs = new LinkedList<>();
        for (RoomProjection roomProjection : roomProjections) {
            roomAvailableDTOs.add(toRoomAvailableDTO(roomProjection));
        }
        return roomAvailableDTOs;
    }

    private List<RoomDTO> toRoomDTOs(Page<RoomProjection> roomProjections, Boolean deleteFlag) {
        List<RoomDTO> roomDTOs = new LinkedList<>();
        if (Boolean.TRUE.equals(deleteFlag)) {
            for (RoomProjection roomProjection : roomProjections) {
                roomDTOs.add(toRoomDTOIsDeleteFlag(roomProjection));
            }
        } else {
            for (RoomProjection roomProjection : roomProjections) {
                roomDTOs.add(toRoomDTO(roomProjection));
            }
        }
        return roomDTOs;
    }

    private RoomAvailableDTO toRoomAvailableDTO(RoomProjection roomProjection) {
        RoomAvailableDTO roomAvailableDTO = roomMapper.roomProjectionToRoomAvailableDTO(roomProjection);
        roomAvailableDTO.setIsAvailable(!roomProjection.getIsAvailable().equals(BigInteger.ZERO));
        List<Media> medias = mediaService.getMediaByRoom(roomAvailableDTO.getId());
        roomAvailableDTO.setMedias(mediaMapper.toMediaDTOs(medias));
        return roomAvailableDTO;
    }

    private RoomDTO toRoomDTO(RoomProjection roomProjection) {
        RoomDTO roomDTO = roomMapper.roomProjectionToRoomDTO(roomProjection);
        List<Media> medias = mediaService.getMediaByRoom(roomDTO.getId());
        roomDTO.setMedias(mediaMapper.toMediaDTOs(medias));
        return roomDTO;
    }

    private RoomDTO toRoomDTOIsDeleteFlag(RoomProjection roomProjection) {
        RoomDTO roomDTO = roomMapper.roomProjectionToRoomDTO(roomProjection);
        List<Media> medias = mediaService.getMediaByRoomAndIsDeleteFlag(roomDTO.getId());
        roomDTO.setMedias(mediaMapper.toMediaDTOs(medias));
        return roomDTO;
    }

    private void checkNotFoundRoomById(Optional<Room> room, Long roomId) {
        if (room.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID, roomId));
        }
    }

    private void checkNotFoundRoomIsDeleteFlagById(Optional<Room> room, Long roomId) {
        if (room.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID_IN_TRASH, roomId));
        }
    }

    private void checkNotFoundRoomById(RoomProjection room, Long roomId) {
        if (ObjectUtils.isEmpty(room)) {
            throw new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID, roomId));
        }
    }

}
