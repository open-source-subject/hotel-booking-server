package com.bookinghotel.mapper;

import com.bookinghotel.dto.RoomRatingCreateDTO;
import com.bookinghotel.dto.RoomRatingDTO;
import com.bookinghotel.dto.RoomRatingUpdateDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.entity.Room;
import com.bookinghotel.entity.RoomRating;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.RoomRatingProjection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RoomRatingMapperImpl implements RoomRatingMapper {

    @Override
    public RoomRatingDTO toRoomRatingDTO(RoomRating roomRating, User createdBy, User lastModifiedBy) {
        if ( roomRating == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        RoomRatingDTO roomRatingDTO = new RoomRatingDTO();

        if ( roomRating != null ) {
            roomRatingDTO.setId( roomRating.getId() );
            roomRatingDTO.setCreatedDate( roomRating.getCreatedDate() );
            roomRatingDTO.setLastModifiedDate( roomRating.getLastModifiedDate() );
            roomRatingDTO.setRoomId( roomRatingRoomId( roomRating ) );
            roomRatingDTO.setStar( roomRating.getStar() );
            roomRatingDTO.setComment( roomRating.getComment() );
        }
        if ( createdBy != null ) {
            roomRatingDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            roomRatingDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return roomRatingDTO;
    }

    @Override
    public RoomRating createDtoToRoomRating(RoomRatingCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        RoomRating roomRating = new RoomRating();

        roomRating.setStar( createDTO.getStar() );
        roomRating.setComment( createDTO.getComment() );

        return roomRating;
    }

    @Override
    public void updateRoomRatingFromDTO(RoomRatingUpdateDTO updateDTO, RoomRating roomRating) {
        if ( updateDTO == null ) {
            return;
        }

        roomRating.setStar( updateDTO.getStar() );
        roomRating.setComment( updateDTO.getComment() );
    }

    @Override
    public RoomRatingDTO roomRatingProjectionToRoomRatingDTO(RoomRatingProjection projection) {
        if ( projection == null ) {
            return null;
        }

        RoomRatingDTO roomRatingDTO = new RoomRatingDTO();

        roomRatingDTO.setId( projection.getId() );
        roomRatingDTO.setStar( projection.getStar() );
        roomRatingDTO.setComment( projection.getComment() );
        roomRatingDTO.setCreatedBy( projection.getCreatedBy() );
        roomRatingDTO.setLastModifiedBy( projection.getLastModifiedBy() );
        if ( projection.getRoomId() != null ) {
            roomRatingDTO.setRoomId( Long.parseLong( projection.getRoomId() ) );
        }

        return roomRatingDTO;
    }

    protected CreatedByDTO userToCreatedByDTO(User user) {
        if ( user == null ) {
            return null;
        }

        CreatedByDTO createdByDTO = new CreatedByDTO();

        createdByDTO.setId( user.getId() );
        createdByDTO.setFirstName( user.getFirstName() );
        createdByDTO.setLastName( user.getLastName() );
        createdByDTO.setAvatar( user.getAvatar() );

        return createdByDTO;
    }

    protected LastModifiedByDTO userToLastModifiedByDTO(User user) {
        if ( user == null ) {
            return null;
        }

        LastModifiedByDTO lastModifiedByDTO = new LastModifiedByDTO();

        lastModifiedByDTO.setId( user.getId() );
        lastModifiedByDTO.setFirstName( user.getFirstName() );
        lastModifiedByDTO.setLastName( user.getLastName() );
        lastModifiedByDTO.setAvatar( user.getAvatar() );

        return lastModifiedByDTO;
    }

    private Long roomRatingRoomId(RoomRating roomRating) {
        if ( roomRating == null ) {
            return null;
        }
        Room room = roomRating.getRoom();
        if ( room == null ) {
            return null;
        }
        Long id = room.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
