package com.bookinghotel.mapper;

import com.bookinghotel.dto.SaleCreateDTO;
import com.bookinghotel.dto.SaleDTO;
import com.bookinghotel.dto.SaleUpdateDTO;
import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.LastModifiedByDTO;
import com.bookinghotel.entity.Sale;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.SaleProjection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T09:20:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class SaleMapperImpl implements SaleMapper {

    @Override
    public SaleDTO toSaleDTO(Sale sale, User createdBy, User lastModifiedBy) {
        if ( sale == null && createdBy == null && lastModifiedBy == null ) {
            return null;
        }

        SaleDTO saleDTO = new SaleDTO();

        if ( sale != null ) {
            saleDTO.setId( sale.getId() );
            saleDTO.setCreatedDate( sale.getCreatedDate() );
            saleDTO.setLastModifiedDate( sale.getLastModifiedDate() );
            saleDTO.setDayStart( sale.getDayStart() );
            saleDTO.setDayEnd( sale.getDayEnd() );
            saleDTO.setSalePercent( sale.getSalePercent() );
        }
        if ( createdBy != null ) {
            saleDTO.setCreatedBy( userToCreatedByDTO( createdBy ) );
        }
        if ( lastModifiedBy != null ) {
            saleDTO.setLastModifiedBy( userToLastModifiedByDTO( lastModifiedBy ) );
        }

        return saleDTO;
    }

    @Override
    public Sale createDtoToSale(SaleCreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        Sale sale = new Sale();

        sale.setDayStart( createDTO.getDayStart() );
        sale.setDayEnd( createDTO.getDayEnd() );
        sale.setSalePercent( createDTO.getSalePercent() );

        return sale;
    }

    @Override
    public void updateSaleFromDTO(SaleUpdateDTO updateDTO, Sale sale) {
        if ( updateDTO == null ) {
            return;
        }

        sale.setDayStart( updateDTO.getDayStart() );
        sale.setDayEnd( updateDTO.getDayEnd() );
        sale.setSalePercent( updateDTO.getSalePercent() );
    }

    @Override
    public SaleDTO saleProjectionToSaleDTO(SaleProjection projection) {
        if ( projection == null ) {
            return null;
        }

        SaleDTO saleDTO = new SaleDTO();

        saleDTO.setCreatedDate( projection.getCreatedDate() );
        saleDTO.setLastModifiedDate( projection.getLastModifiedDate() );
        saleDTO.setId( projection.getId() );
        saleDTO.setDayStart( projection.getDayStart() );
        saleDTO.setDayEnd( projection.getDayEnd() );
        saleDTO.setSalePercent( projection.getSalePercent() );
        saleDTO.setCreatedBy( projection.getCreatedBy() );
        saleDTO.setLastModifiedBy( projection.getLastModifiedBy() );

        return saleDTO;
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
