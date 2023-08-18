package com.bookinghotel.mapper;

import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.dto.ServiceSummaryDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.entity.Product;
import com.bookinghotel.entity.Service;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.ProductProjection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:27+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toProductDTO(Product product, User createdBy, User lastModifiedBy) {
        if ( product == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        if ( product != null ) {
            productDTO.setId( product.getId() );
            productDTO.setCreatedDate( product.getCreatedDate() );
            productDTO.setLastModifiedDate( product.getLastModifiedDate() );
            productDTO.setName( product.getName() );
            productDTO.setThumbnail( product.getThumbnail() );
            productDTO.setDescription( product.getDescription() );
            productDTO.setService( serviceToServiceSummaryDTO( product.getService() ) );
        }
        if ( createdBy != null ) {
            productDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            productDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return productDTO;
    }

    @Override
    public Product createDtoToProduct(ProductCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( createDTO.getName() );
        product.setDescription( createDTO.getDescription() );

        return product;
    }

    @Override
    public void updateProductFromDTO(ProductUpdateDTO updateDTO, Product product) {
        if ( updateDTO == null ) {
            return;
        }

        product.setName( updateDTO.getName() );
        product.setDescription( updateDTO.getDescription() );
    }

    @Override
    public ProductDTO productProjectionToProductDTO(ProductProjection projection) {
        if ( projection == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCreatedDate( projection.getCreatedDate() );
        productDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        productDTO.setId( projection.getId() );
        productDTO.setName( projection.getName() );
        productDTO.setThumbnail( projection.getThumbnail() );
        productDTO.setDescription( projection.getDescription() );
        productDTO.setService( projection.getService() );
        productDTO.setCreatedBy( projection.getCreatedBy() );
        productDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return productDTO;
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

    protected ServiceSummaryDTO serviceToServiceSummaryDTO(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceSummaryDTO serviceSummaryDTO = new ServiceSummaryDTO();

        serviceSummaryDTO.setId( service.getId() );
        serviceSummaryDTO.setTitle( service.getTitle() );
        serviceSummaryDTO.setThumbnail( service.getThumbnail() );
        serviceSummaryDTO.setPrice( service.getPrice() );

        return serviceSummaryDTO;
    }
}
