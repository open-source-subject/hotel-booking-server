package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.RoomAvailableDTO;
import com.bookinghotel.dto.RoomCreateDTO;
import com.bookinghotel.dto.RoomDTO;
import com.bookinghotel.dto.RoomSummaryDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.dto.SaleSummaryDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.dto.init.RoomInitJSON;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Room;
import com.bookinghotel.entity.Sale;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.RoomProjection;
import com.bookinghotel.repository.projection.StatisticRoomBookedProjection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDTO toRoomDTO(Room room, User createdBy, User lastModifiedBy) {
        if ( room == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        if ( room != null ) {
            roomDTO.setId( room.getId() );
            roomDTO.setCreatedDate( room.getCreatedDate() );
            roomDTO.setLastModifiedDate( room.getLastModifiedDate() );
            roomDTO.setName( room.getName() );
            roomDTO.setPrice( room.getPrice() );
            roomDTO.setType( room.getType() );
            roomDTO.setBed( room.getBed() );
            roomDTO.setSize( room.getSize() );
            roomDTO.setCapacity( room.getCapacity() );
            roomDTO.setServices( room.getServices() );
            roomDTO.setDescription( room.getDescription() );
            roomDTO.setSale( saleToSaleSummaryDTO( room.getSale() ) );
            roomDTO.setMedias( mediaSetToMediaDTOList( room.getMedias() ) );
        }
        if ( createdBy != null ) {
            roomDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            roomDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return roomDTO;
    }

    @Override
    public Room createDtoToRoom(RoomCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setName( createDTO.getName() );
        room.setPrice( createDTO.getPrice() );
        room.setType( createDTO.getType() );
        room.setBed( createDTO.getBed() );
        room.setSize( createDTO.getSize() );
        room.setCapacity( createDTO.getCapacity() );
        room.setServices( createDTO.getServices() );
        room.setDescription( createDTO.getDescription() );

        return room;
    }

    @Override
    public void updateRoomFromDTO(RoomUpdateDTO updateDTO, Room room) {
        if ( updateDTO == null ) {
            return;
        }

        room.setName( updateDTO.getName() );
        room.setPrice( updateDTO.getPrice() );
        room.setType( updateDTO.getType() );
        room.setBed( updateDTO.getBed() );
        room.setSize( updateDTO.getSize() );
        room.setCapacity( updateDTO.getCapacity() );
        room.setServices( updateDTO.getServices() );
        room.setDescription( updateDTO.getDescription() );
    }

    @Override
    public RoomDTO roomProjectionToRoomDTO(RoomProjection projection) {
        if ( projection == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setCreatedDate( projection.getCreatedDate() );
        roomDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        roomDTO.setId( projection.getId() );
        roomDTO.setName( projection.getName() );
        roomDTO.setPrice( projection.getPrice() );
        roomDTO.setType( projection.getType() );
        roomDTO.setBed( projection.getBed() );
        roomDTO.setSize( projection.getSize() );
        roomDTO.setCapacity( projection.getCapacity() );
        roomDTO.setServices( projection.getServices() );
        roomDTO.setDescription( projection.getDescription() );
        roomDTO.setSale( projection.getSale() );
        roomDTO.setCreatedBy( projection.getCreatedBy() );
        roomDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return roomDTO;
    }

    @Override
    public RoomAvailableDTO roomProjectionToRoomAvailableDTO(RoomProjection projection) {
        if ( projection == null ) {
            return null;
        }

        RoomAvailableDTO roomAvailableDTO = new RoomAvailableDTO();

        roomAvailableDTO.setCreatedDate( projection.getCreatedDate() );
        roomAvailableDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        roomAvailableDTO.setId( projection.getId() );
        roomAvailableDTO.setName( projection.getName() );
        roomAvailableDTO.setPrice( projection.getPrice() );
        roomAvailableDTO.setType( projection.getType() );
        roomAvailableDTO.setBed( projection.getBed() );
        roomAvailableDTO.setSize( projection.getSize() );
        roomAvailableDTO.setCapacity( projection.getCapacity() );
        roomAvailableDTO.setServices( projection.getServices() );
        roomAvailableDTO.setDescription( projection.getDescription() );
        roomAvailableDTO.setSale( projection.getSale() );
        roomAvailableDTO.setCreatedBy( projection.getCreatedBy() );
        roomAvailableDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return roomAvailableDTO;
    }

    @Override
    public RoomSummaryDTO statisticRoomToRoomDTO(StatisticRoomBookedProjection projection) {
        if ( projection == null ) {
            return null;
        }

        RoomSummaryDTO roomSummaryDTO = new RoomSummaryDTO();

        roomSummaryDTO.setId( projection.getId() );
        roomSummaryDTO.setName( projection.getName() );
        roomSummaryDTO.setPrice( projection.getPrice() );
        roomSummaryDTO.setType( projection.getType() );
        roomSummaryDTO.setBed( projection.getBed() );
        roomSummaryDTO.setSize( projection.getSize() );
        roomSummaryDTO.setCapacity( projection.getCapacity() );
        roomSummaryDTO.setServices( projection.getServices() );
        roomSummaryDTO.setDescription( projection.getDescription() );

        return roomSummaryDTO;
    }

    @Override
    public Room roomInitToRoom(RoomInitJSON initJSON) {
        if ( initJSON == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( initJSON.getId() );
        room.setName( initJSON.getName() );
        room.setPrice( initJSON.getPrice() );
        room.setType( initJSON.getType() );
        room.setBed( initJSON.getBed() );
        room.setSize( initJSON.getSize() );
        room.setCapacity( initJSON.getCapacity() );
        room.setServices( initJSON.getServices() );
        room.setDescription( initJSON.getDescription() );
        Set<Media> set = initJSON.getMedias();
        if ( set != null ) {
            room.setMedias( new HashSet<Media>( set ) );
        }

        return room;
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

    protected SaleSummaryDTO saleToSaleSummaryDTO(Sale sale) {
        if ( sale == null ) {
            return null;
        }

        SaleSummaryDTO saleSummaryDTO = new SaleSummaryDTO();

        saleSummaryDTO.setId( sale.getId() );
        saleSummaryDTO.setDayStart( sale.getDayStart() );
        saleSummaryDTO.setDayEnd( sale.getDayEnd() );
        saleSummaryDTO.setSalePercent( sale.getSalePercent() );

        return saleSummaryDTO;
    }

    protected MediaDTO mediaToMediaDTO(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDTO mediaDTO = new MediaDTO();

        mediaDTO.setId( media.getId() );
        mediaDTO.setUrl( media.getUrl() );
        mediaDTO.setType( media.getType() );

        return mediaDTO;
    }

    protected List<MediaDTO> mediaSetToMediaDTOList(Set<Media> set) {
        if ( set == null ) {
            return null;
        }

        List<MediaDTO> list = new ArrayList<MediaDTO>( set.size() );
        for ( Media media : set ) {
            list.add( mediaToMediaDTO( media ) );
        }

        return list;
    }
}
