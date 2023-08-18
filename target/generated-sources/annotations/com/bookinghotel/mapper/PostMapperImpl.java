package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Post;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.PostProjection;
import java.util.ArrayList;
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
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO toPostDTO(Post post, User createdBy, User lastModifiedBy) {
        if ( post == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        if ( post != null ) {
            postDTO.setId( post.getId() );
            postDTO.setCreatedDate( post.getCreatedDate() );
            postDTO.setLastModifiedDate( post.getLastModifiedDate() );
            postDTO.setTitle( post.getTitle() );
            postDTO.setContent( post.getContent() );
            postDTO.setMedias( mediaSetToMediaDTOList( post.getMedias() ) );
        }
        if ( createdBy != null ) {
            postDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            postDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return postDTO;
    }

    @Override
    public PostDTO toPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setCreatedDate( post.getCreatedDate() );
        postDTO.setLastModifiedDate( post.getLastModifiedDate() );
        postDTO.setId( post.getId() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setContent( post.getContent() );
        postDTO.setMedias( mediaSetToMediaDTOList( post.getMedias() ) );

        return postDTO;
    }

    @Override
    public Post createDtoToPost(PostCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setTitle( createDTO.getTitle() );
        post.setContent( createDTO.getContent() );

        return post;
    }

    @Override
    public void updatePostFromDTO(PostUpdateDTO updateDTO, Post post) {
        if ( updateDTO == null ) {
            return;
        }

        post.setTitle( updateDTO.getTitle() );
        post.setContent( updateDTO.getContent() );
    }

    @Override
    public PostDTO postProjectionToPostDTO(PostProjection projection) {
        if ( projection == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setCreatedDate( projection.getCreatedDate() );
        postDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        postDTO.setId( projection.getId() );
        postDTO.setTitle( projection.getTitle() );
        postDTO.setContent( projection.getContent() );
        postDTO.setCreatedBy( projection.getCreatedBy() );
        postDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return postDTO;
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
