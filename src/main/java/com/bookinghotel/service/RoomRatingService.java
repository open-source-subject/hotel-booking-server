package com.bookinghotel.service;

import com.bookinghotel.dto.RoomRatingCreateDTO;
import com.bookinghotel.dto.RoomRatingDTO;
import com.bookinghotel.dto.RoomRatingUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface RoomRatingService {

    RoomRatingDTO getRoomRating(Long id);

    PaginationResponseDTO<RoomRatingDTO> getRoomRatingsByRoom(PaginationSortRequestDTO requestDTO, Integer star, Long roomId);

    RoomRatingDTO createRoomRating(Long roomId, RoomRatingCreateDTO roomRatingCreateDTO, UserPrincipal currentUser);

    RoomRatingDTO updateRoomRating(Long id, RoomRatingUpdateDTO roomRatingUpdateDTO, UserPrincipal currentUser);

    CommonResponseDTO deleteRoomRating(Long id, UserPrincipal currentUser);

    void deleteRoomRatingByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

}
