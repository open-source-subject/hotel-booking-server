package com.bookinghotel.mapper;

import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.dto.init.ServiceInitJSON;
import com.bookinghotel.entity.Product;
import com.bookinghotel.entity.Service;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.ServiceProjection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceDTO toServiceDTO(Service service, User createdBy, User lastModifiedBy) {
        if ( service == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        ServiceDTO serviceDTO = new ServiceDTO();

        if ( service != null ) {
            serviceDTO.setId( service.getId() );
            serviceDTO.setCreatedDate( service.getCreatedDate() );
            serviceDTO.setLastModifiedDate( service.getLastModifiedDate() );
            serviceDTO.setTitle( service.getTitle() );
            serviceDTO.setThumbnail( service.getThumbnail() );
            serviceDTO.setPrice( service.getPrice() );
            serviceDTO.setDescription( service.getDescription() );
        }
        if ( createdBy != null ) {
            serviceDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            serviceDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return serviceDTO;
    }

    @Override
    public Service createDtoToProduct(ServiceCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        Service service = new Service();

        service.setTitle( createDTO.getTitle() );
        service.setPrice( createDTO.getPrice() );
        service.setDescription( createDTO.getDescription() );

        return service;
    }

    @Override
    public void updateProductFromDTO(ServiceUpdateDTO updateDTO, Service service) {
        if ( updateDTO == null ) {
            return;
        }

        service.setTitle( updateDTO.getTitle() );
        service.setPrice( updateDTO.getPrice() );
        service.setDescription( updateDTO.getDescription() );
    }

    @Override
    public ServiceDTO serviceProjectionToServiceDTO(ServiceProjection projection) {
        if ( projection == null ) {
            return null;
        }

        ServiceDTO serviceDTO = new ServiceDTO();

        serviceDTO.setCreatedDate( projection.getCreatedDate() );
        serviceDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        serviceDTO.setId( projection.getId() );
        serviceDTO.setTitle( projection.getTitle() );
        serviceDTO.setThumbnail( projection.getThumbnail() );
        serviceDTO.setPrice( projection.getPrice() );
        serviceDTO.setDescription( projection.getDescription() );
        serviceDTO.setCreatedBy( projection.getCreatedBy() );
        serviceDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return serviceDTO;
    }

    @Override
    public Service serviceInitToService(ServiceInitJSON initJSON) {
        if ( initJSON == null ) {
            return null;
        }

        Service service = new Service();

        service.setId( initJSON.getId() );
        service.setTitle( initJSON.getTitle() );
        service.setThumbnail( initJSON.getThumbnail() );
        service.setPrice( initJSON.getPrice() );
        service.setDescription( initJSON.getDescription() );
        Set<Product> set = initJSON.getProducts();
        if ( set != null ) {
            service.setProducts( new HashSet<Product>( set ) );
        }

        return service;
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
}
