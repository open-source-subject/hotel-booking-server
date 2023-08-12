package com.bookinghotel.service;

import com.bookinghotel.dto.*;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface RoomService {

    RoomDTO getRoom(Long roomId);

    PaginationResponseDTO<RoomDTO> getRooms(PaginationSearchSortRequestDTO requestDTO, String roomType, Boolean deleteFlag);

    PaginationResponseDTO<RoomAvailableDTO> getRoomsAvailable(PaginationSearchSortRequestDTO requestDTO, RoomFilterDTO roomFilterDTO);

    RoomDTO createRoom(RoomCreateDTO roomCreateDTO, UserPrincipal currentUser);

    RoomDTO updateRoom(Long roomId, RoomUpdateDTO roomUpdateDTO, UserPrincipal currentUser);

    CommonResponseDTO deleteRoom(Long roomId);

    CommonResponseDTO deleteRoomPermanently(Long roomId);

    CommonResponseDTO restoreRoom(Long roomId);

    void deleteRoomByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

}
