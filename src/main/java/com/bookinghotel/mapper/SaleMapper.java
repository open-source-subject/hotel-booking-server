package com.bookinghotel.mapper;

import com.bookinghotel.dto.SaleCreateDTO;
import com.bookinghotel.dto.SaleDTO;
import com.bookinghotel.dto.SaleUpdateDTO;
import com.bookinghotel.entity.Sale;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.SaleProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mappings({
            @Mapping(target = "id", source = "sale.id"),
            @Mapping(target = "createdDate", source = "sale.createdDate"),
            @Mapping(target = "lastModifiedDate", source = "sale.lastModifiedDate"),
            @Mapping(target = "createdBy.id", source = "createdBy.id"),
            @Mapping(target = "createdBy.firstName", source = "createdBy.firstName"),
            @Mapping(target = "createdBy.lastName", source = "createdBy.lastName"),
            @Mapping(target = "createdBy.avatar", source = "createdBy.avatar"),
            @Mapping(target = "lastModifiedBy.id", source = "lastModifiedBy.id"),
            @Mapping(target = "lastModifiedBy.firstName", source = "lastModifiedBy.firstName"),
            @Mapping(target = "lastModifiedBy.lastName", source = "lastModifiedBy.lastName"),
            @Mapping(target = "lastModifiedBy.avatar", source = "lastModifiedBy.avatar"),
    })
    SaleDTO toSaleDTO(Sale sale, User createdBy, User lastModifiedBy);

    Sale createDtoToSale(SaleCreateDTO createDTO);

    void updateSaleFromDTO(SaleUpdateDTO updateDTO, @MappingTarget Sale sale);

    SaleDTO saleProjectionToSaleDTO(SaleProjection projection);

}
