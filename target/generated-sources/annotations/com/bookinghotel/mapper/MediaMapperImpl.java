package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.MediaDetailDTO;
import com.bookinghotel.entity.Media;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class MediaMapperImpl implements MediaMapper {

    @Override
    public Media toMedia(MediaDTO mediaDTO) {
        if ( mediaDTO == null ) {
            return null;
        }

        Media media = new Media();

        media.setId( mediaDTO.getId() );
        media.setUrl( mediaDTO.getUrl() );
        media.setType( mediaDTO.getType() );

        return media;
    }

    @Override
    public List<Media> toMedias(List<MediaDTO> mediaDTO) {
        if ( mediaDTO == null ) {
            return null;
        }

        List<Media> list = new ArrayList<Media>( mediaDTO.size() );
        for ( MediaDTO mediaDTO1 : mediaDTO ) {
            list.add( toMedia( mediaDTO1 ) );
        }

        return list;
    }

    @Override
    public MediaDTO toMediaDTO(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDTO mediaDTO = new MediaDTO();

        mediaDTO.setId( media.getId() );
        mediaDTO.setUrl( media.getUrl() );
        mediaDTO.setType( media.getType() );

        return mediaDTO;
    }

    @Override
    public List<MediaDTO> toMediaDTOs(List<Media> medias) {
        if ( medias == null ) {
            return null;
        }

        List<MediaDTO> list = new ArrayList<MediaDTO>( medias.size() );
        for ( Media media : medias ) {
            list.add( toMediaDTO( media ) );
        }

        return list;
    }

    @Override
    public List<MediaDetailDTO> toMediaDetailDTOs(List<Media> medias) {
        if ( medias == null ) {
            return null;
        }

        List<MediaDetailDTO> list = new ArrayList<MediaDetailDTO>( medias.size() );
        for ( Media media : medias ) {
            list.add( mediaToMediaDetailDTO( media ) );
        }

        return list;
    }

    protected MediaDetailDTO mediaToMediaDetailDTO(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDetailDTO mediaDetailDTO = new MediaDetailDTO();

        mediaDetailDTO.setId( media.getId() );
        mediaDetailDTO.setUrl( media.getUrl() );
        mediaDetailDTO.setType( media.getType() );

        return mediaDetailDTO;
    }
}
