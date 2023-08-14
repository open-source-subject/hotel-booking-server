package com.bookinghotel.mapper;

import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.entity.Product;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.ProductProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mappings({
            @Mapping(target = "id", source = "product.id"),
            @Mapping(target = "createdDate", source = "product.createdDate"),
            @Mapping(target = "lastModifiedDate", source = "product.lastModifiedDate"),
            @Mapping(target = "createdBy.id", source = "createdBy.id"),
            @Mapping(target = "createdBy.firstName", source = "createdBy.firstName"),
            @Mapping(target = "createdBy.lastName", source = "createdBy.lastName"),
            @Mapping(target = "createdBy.avatar", source = "createdBy.avatar"),
            @Mapping(target = "lastModifiedBy.id", source = "lastModifiedBy.id"),
            @Mapping(target = "lastModifiedBy.firstName", source = "lastModifiedBy.firstName"),
            @Mapping(target = "lastModifiedBy.lastName", source = "lastModifiedBy.lastName"),
            @Mapping(target = "lastModifiedBy.avatar", source = "lastModifiedBy.avatar"),
    })
    ProductDTO toProductDTO(Product product, User createdBy, User lastModifiedBy);

    Product createDtoToProduct(ProductCreateDTO createDTO);

    @Mapping(target = "thumbnail", ignore = true)
    void updateProductFromDTO(ProductUpdateDTO updateDTO, @MappingTarget Product product);

    ProductDTO productProjectionToProductDTO(ProductProjection projection);

}
