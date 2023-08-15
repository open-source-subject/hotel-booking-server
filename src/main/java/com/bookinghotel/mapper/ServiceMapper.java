package com.bookinghotel.mapper;

import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.init.ServiceInitJSON;
import com.bookinghotel.entity.Service;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.projection.ServiceProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mappings({
            @Mapping(target = "id", source = "service.id"),
            @Mapping(target = "createdDate", source = "service.createdDate"),
            @Mapping(target = "lastModifiedDate", source = "service.lastModifiedDate"),
            @Mapping(target = "createdBy.id", source = "createdBy.id"),
            @Mapping(target = "createdBy.firstName", source = "createdBy.firstName"),
            @Mapping(target = "createdBy.lastName", source = "createdBy.lastName"),
            @Mapping(target = "createdBy.avatar", source = "createdBy.avatar"),
            @Mapping(target = "lastModifiedBy.id", source = "lastModifiedBy.id"),
            @Mapping(target = "lastModifiedBy.firstName", source = "lastModifiedBy.firstName"),
            @Mapping(target = "lastModifiedBy.lastName", source = "lastModifiedBy.lastName"),
            @Mapping(target = "lastModifiedBy.avatar", source = "lastModifiedBy.avatar"),
    })
    ServiceDTO toServiceDTO(Service service, User createdBy, User lastModifiedBy);

    Service createDtoToProduct(ServiceCreateDTO createDTO);

    @Mapping(target = "thumbnail", ignore = true)
    void updateProductFromDTO(ServiceUpdateDTO updateDTO, @MappingTarget Service service);

    ServiceDTO serviceProjectionToServiceDTO(ServiceProjection projection);

    Service serviceInitToService(ServiceInitJSON initJSON);

}
