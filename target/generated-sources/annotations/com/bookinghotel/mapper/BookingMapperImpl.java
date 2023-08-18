package com.bookinghotel.mapper;

import com.bookinghotel.dto.BookingDTO;
import com.bookinghotel.dto.BookingRoomDetailDTO;
import com.bookinghotel.dto.BookingServiceDetailDTO;
import com.bookinghotel.dto.BookingUpdateDTO;
import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.ServiceSummaryDTO;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingRoomDetail;
import com.bookinghotel.entity.BookingServiceDetail;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Room;
import com.bookinghotel.entity.Service;
import com.bookinghotel.repository.projection.BookingProjection;
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
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking toBookingCreated(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        Booking booking1 = new Booking();

        booking1.setCreatedDate( booking.getCreatedDate() );
        booking1.setLastModifiedDate( booking.getLastModifiedDate() );
        booking1.setCreatedBy( booking.getCreatedBy() );
        booking1.setLastModifiedBy( booking.getLastModifiedBy() );
        booking1.setId( booking.getId() );
        booking1.setExpectedCheckIn( booking.getExpectedCheckIn() );
        booking1.setExpectedCheckOut( booking.getExpectedCheckOut() );
        booking1.setCheckIn( booking.getCheckIn() );
        booking1.setCheckOut( booking.getCheckOut() );
        booking1.setStatus( booking.getStatus() );
        booking1.setNote( booking.getNote() );
        booking1.setUser( booking.getUser() );
        Set<BookingRoomDetail> set = booking.getBookingRoomDetails();
        if ( set != null ) {
            booking1.setBookingRoomDetails( new HashSet<BookingRoomDetail>( set ) );
        }
        Set<BookingServiceDetail> set1 = booking.getBookingServiceDetails();
        if ( set1 != null ) {
            booking1.setBookingServiceDetails( new HashSet<BookingServiceDetail>( set1 ) );
        }

        return booking1;
    }

    @Override
    public BookingDTO toBookingDTO(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setCreatedDate( booking.getCreatedDate() );
        bookingDTO.setLastModifiedDate( booking.getLastModifiedDate() );
        bookingDTO.setId( booking.getId() );
        bookingDTO.setExpectedCheckIn( booking.getExpectedCheckIn() );
        bookingDTO.setExpectedCheckOut( booking.getExpectedCheckOut() );
        bookingDTO.setCheckIn( booking.getCheckIn() );
        bookingDTO.setCheckOut( booking.getCheckOut() );
        bookingDTO.setStatus( booking.getStatus() );
        bookingDTO.setNote( booking.getNote() );

        return bookingDTO;
    }

    @Override
    public BookingDTO toBookingDTO(BookingProjection booking) {
        if ( booking == null ) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setCreatedDate( booking.getCreatedDate() );
        bookingDTO.setLastModifiedDate( booking.getLastModifiedDate() );
        bookingDTO.setId( booking.getId() );
        bookingDTO.setExpectedCheckIn( booking.getExpectedCheckIn() );
        bookingDTO.setExpectedCheckOut( booking.getExpectedCheckOut() );
        bookingDTO.setCheckIn( booking.getCheckIn() );
        bookingDTO.setCheckOut( booking.getCheckOut() );
        bookingDTO.setStatus( booking.getStatus() );
        bookingDTO.setNote( booking.getNote() );
        bookingDTO.setUser( booking.getUser() );
        bookingDTO.setCreatedBy( booking.getCreatedBy() );
        bookingDTO.setLastModifiedBy( booking.getLastModifiedBy() );

        return bookingDTO;
    }

    @Override
    public BookingRoomDetailDTO toBookingRoomDetailDTO(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }

        BookingRoomDetailDTO bookingRoomDetailDTO = new BookingRoomDetailDTO();

        bookingRoomDetailDTO.setId( bookingRoomDetailRoomId( bookingRoomDetail ) );
        bookingRoomDetailDTO.setName( bookingRoomDetailRoomName( bookingRoomDetail ) );
        bookingRoomDetailDTO.setType( bookingRoomDetailRoomType( bookingRoomDetail ) );
        bookingRoomDetailDTO.setBed( bookingRoomDetailRoomBed( bookingRoomDetail ) );
        bookingRoomDetailDTO.setSize( bookingRoomDetailRoomSize( bookingRoomDetail ) );
        bookingRoomDetailDTO.setCapacity( bookingRoomDetailRoomCapacity( bookingRoomDetail ) );
        bookingRoomDetailDTO.setServices( bookingRoomDetailRoomServices( bookingRoomDetail ) );
        bookingRoomDetailDTO.setDescription( bookingRoomDetailRoomDescription( bookingRoomDetail ) );
        bookingRoomDetailDTO.setPrice( bookingRoomDetail.getPrice() );
        bookingRoomDetailDTO.setSalePercent( bookingRoomDetail.getSalePercent() );
        Set<Media> medias = bookingRoomDetailRoomMedias( bookingRoomDetail );
        bookingRoomDetailDTO.setMedias( mediaSetToMediaDTOList( medias ) );

        return bookingRoomDetailDTO;
    }

    @Override
    public List<BookingRoomDetailDTO> toBookingRoomDetailDTOs(Set<BookingRoomDetail> bookingRoomDetails) {
        if ( bookingRoomDetails == null ) {
            return null;
        }

        List<BookingRoomDetailDTO> list = new ArrayList<BookingRoomDetailDTO>( bookingRoomDetails.size() );
        for ( BookingRoomDetail bookingRoomDetail : bookingRoomDetails ) {
            list.add( toBookingRoomDetailDTO( bookingRoomDetail ) );
        }

        return list;
    }

    @Override
    public BookingServiceDetailDTO toBookingServiceDetailDTO(BookingServiceDetail bookingServiceDetail) {
        if ( bookingServiceDetail == null ) {
            return null;
        }

        BookingServiceDetailDTO bookingServiceDetailDTO = new BookingServiceDetailDTO();

        if ( bookingServiceDetail.getService() != null ) {
            if ( bookingServiceDetailDTO.getService() == null ) {
                bookingServiceDetailDTO.setService( new ServiceSummaryDTO() );
            }
            serviceToServiceSummaryDTO( bookingServiceDetail.getService(), bookingServiceDetailDTO.getService() );
        }
        if ( bookingServiceDetailDTO.getService() == null ) {
            bookingServiceDetailDTO.setService( new ServiceSummaryDTO() );
        }
        bookingServiceDetailToServiceSummaryDTO( bookingServiceDetail, bookingServiceDetailDTO.getService() );
        bookingServiceDetailDTO.setAmount( bookingServiceDetail.getAmount() );

        return bookingServiceDetailDTO;
    }

    @Override
    public List<BookingServiceDetailDTO> toBookingServiceDetailDTOs(Set<BookingServiceDetail> bookingServiceDetails) {
        if ( bookingServiceDetails == null ) {
            return null;
        }

        List<BookingServiceDetailDTO> list = new ArrayList<BookingServiceDetailDTO>( bookingServiceDetails.size() );
        for ( BookingServiceDetail bookingServiceDetail : bookingServiceDetails ) {
            list.add( toBookingServiceDetailDTO( bookingServiceDetail ) );
        }

        return list;
    }

    @Override
    public void updateBookingFromDTO(BookingUpdateDTO updateDTO, Booking booking) {
        if ( updateDTO == null ) {
            return;
        }

        booking.setCheckIn( updateDTO.getCheckIn() );
        booking.setCheckOut( updateDTO.getCheckOut() );
        booking.setStatus( updateDTO.getStatus() );
        booking.setNote( updateDTO.getNote() );
    }

    private Long bookingRoomDetailRoomId(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        Long id = room.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String bookingRoomDetailRoomName(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        String name = room.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String bookingRoomDetailRoomType(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        String type = room.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }

    private String bookingRoomDetailRoomBed(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        String bed = room.getBed();
        if ( bed == null ) {
            return null;
        }
        return bed;
    }

    private Integer bookingRoomDetailRoomSize(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        Integer size = room.getSize();
        if ( size == null ) {
            return null;
        }
        return size;
    }

    private Integer bookingRoomDetailRoomCapacity(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        Integer capacity = room.getCapacity();
        if ( capacity == null ) {
            return null;
        }
        return capacity;
    }

    private String bookingRoomDetailRoomServices(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        String services = room.getServices();
        if ( services == null ) {
            return null;
        }
        return services;
    }

    private String bookingRoomDetailRoomDescription(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        String description = room.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }

    private Set<Media> bookingRoomDetailRoomMedias(BookingRoomDetail bookingRoomDetail) {
        if ( bookingRoomDetail == null ) {
            return null;
        }
        Room room = bookingRoomDetail.getRoom();
        if ( room == null ) {
            return null;
        }
        Set<Media> medias = room.getMedias();
        if ( medias == null ) {
            return null;
        }
        return medias;
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

    protected void serviceToServiceSummaryDTO(Service service, ServiceSummaryDTO mappingTarget) {
        if ( service == null ) {
            return;
        }

        mappingTarget.setId( service.getId() );
        mappingTarget.setTitle( service.getTitle() );
        mappingTarget.setThumbnail( service.getThumbnail() );
        mappingTarget.setPrice( service.getPrice() );
    }

    protected void bookingServiceDetailToServiceSummaryDTO(BookingServiceDetail bookingServiceDetail, ServiceSummaryDTO mappingTarget) {
        if ( bookingServiceDetail == null ) {
            return;
        }

        mappingTarget.setPrice( bookingServiceDetail.getPrice() );
    }
}
