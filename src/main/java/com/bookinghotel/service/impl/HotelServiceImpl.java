package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Service;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.ServiceMapper;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.repository.projection.ServiceProjection;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.HotelService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class HotelServiceImpl implements HotelService {

    private final ServiceRepository serviceRepository;

    private final UserRepository userRepository;

    private final ServiceMapper serviceMapper;

    private final UploadFileUtil uploadFile;

    @Override
    public ServiceDTO getServiceById(Long serviceId) {
        ServiceProjection service = serviceRepository.findServiceById(serviceId);
        checkNotFoundServiceById(service, serviceId);
        return serviceMapper.serviceProjectionToServiceDTO(service);
    }

    @Override
    public PaginationResponseDTO<ServiceDTO> getServices(PaginationSearchSortRequestDTO requestDTO, Boolean deleteFlag) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.SERVICE);
        Page<ServiceProjection> services = serviceRepository.findAllService(requestDTO.getKeyword(), deleteFlag,
                pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.SERVICE, services);
        return new PaginationResponseDTO<>(meta, toServiceDTOs(services));
    }

    @Override
    public ServiceDTO createService(ServiceCreateDTO serviceCreateDTO, UserPrincipal principal) {
        User creator = userRepository.getUser(principal);
        Service service = serviceMapper.createDtoToProduct(serviceCreateDTO);
        service.setThumbnail(uploadFile.uploadFile(serviceCreateDTO.getThumbnailFile()));
        return serviceMapper.toServiceDTO(serviceRepository.save(service), creator, creator);
    }

    @Override
    public ServiceDTO updateService(Long serviceId, ServiceUpdateDTO serviceUpdateDTO, UserPrincipal principal) {
        Optional<Service> currentService = serviceRepository.findById(serviceId);
        checkNotFoundServiceById(currentService, serviceId);
        serviceMapper.updateProductFromDTO(serviceUpdateDTO, currentService.get());
        //update thumbnail
        if (ObjectUtils.isEmpty(serviceUpdateDTO.getThumbnail())) {
            if (serviceUpdateDTO.getThumbnailFile() != null) {
                uploadFile.destroyFileWithUrl(currentService.get().getThumbnail());
                currentService.get().setThumbnail(uploadFile.uploadFile(serviceUpdateDTO.getThumbnailFile()));
            } else {
                throw new InvalidException(ErrorMessage.Service.ERR_SERVICE_MUST_HAVE_THUMBNAIL);
            }
        }
        User updater = userRepository.getUser(principal);
        User creator = userRepository.findById(currentService.get().getCreatedBy()).get();
        return serviceMapper.toServiceDTO(serviceRepository.save(currentService.get()), creator, updater);
    }

    @Override
    public CommonResponseDTO deleteService(Long serviceId) {
        Optional<Service> service = serviceRepository.findById(serviceId);
        checkNotFoundServiceById(service, serviceId);
        service.get().setDeleteFlag(CommonConstant.TRUE);
        serviceRepository.save(service.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO deleteServicePermanently(Long serviceId) {
        Optional<Service> service = serviceRepository.findByIdAndIsDeleteFlag(serviceId);
        checkNotFoundServiceIsDeleteFlagById(service, serviceId);
        serviceRepository.delete(service.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO restoreService(Long serviceId) {
        Optional<Service> service = serviceRepository.findByIdAndIsDeleteFlag(serviceId);
        checkNotFoundServiceIsDeleteFlagById(service, serviceId);
        service.get().setDeleteFlag(CommonConstant.FALSE);
        serviceRepository.save(service.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.RESTORE_SUCCESS);
    }

    @Override
    @Transactional
    public void deleteServiceByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords) {
        serviceRepository.deleteByDeleteFlag(isDeleteFlag, daysToDeleteRecords);
    }

    private List<ServiceDTO> toServiceDTOs(Page<ServiceProjection> serviceProjections) {
        List<ServiceDTO> serviceDTOs = new LinkedList<>();
        for (ServiceProjection serviceProjection : serviceProjections) {
            serviceDTOs.add(serviceMapper.serviceProjectionToServiceDTO(serviceProjection));
        }
        return serviceDTOs;
    }

    private void checkNotFoundServiceById(Optional<Service> service, Long serviceId) {
        if (service.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, serviceId));
        }
    }

    private void checkNotFoundServiceIsDeleteFlagById(Optional<Service> service, Long serviceId) {
        if (service.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID_IN_TRASH, serviceId));
        }
    }

    private void checkNotFoundServiceById(ServiceProjection serviceProjection, Long serviceId) {
        if (ObjectUtils.isEmpty(serviceProjection)) {
            throw new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, serviceId));
        }
    }
}
