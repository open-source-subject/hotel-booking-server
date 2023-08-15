package com.bookinghotel.service;

import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface HotelService {

    ServiceDTO getServiceById(Long serviceId);

    PaginationResponseDTO<ServiceDTO> getServices(PaginationSearchSortRequestDTO requestDTO, Boolean deleteFlag);

    ServiceDTO createService(ServiceCreateDTO serviceCreateDTO, UserPrincipal principal);

    ServiceDTO updateService(Long serviceId, ServiceUpdateDTO serviceUpdateDTO, UserPrincipal principal);

    CommonResponseDTO deleteService(Long serviceId);

    CommonResponseDTO deleteServicePermanently(Long serviceId);

    CommonResponseDTO restoreService(Long serviceId);

    void deleteServiceByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

}
