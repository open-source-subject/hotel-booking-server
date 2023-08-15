package com.bookinghotel.service.impl;

import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.dto.BookingServiceDTO;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingServiceDetail;
import com.bookinghotel.entity.Service;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.repository.BookingServiceDetailRepository;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.service.BookingServiceDetailService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class BookingServiceDetailServiceImpl implements BookingServiceDetailService {

    private final BookingServiceDetailRepository bookingServiceDetailRepository;

    private final ServiceRepository serviceRepository;

    @Override
    public Set<BookingServiceDetail> getBookingServiceDetailsByBooking(Long bookingId) {
        return bookingServiceDetailRepository.findAllByBookingId(bookingId);
    }

    @Override
    public Set<BookingServiceDetail> createBookingServiceDetails(Booking booking,
                                                                 List<BookingServiceDTO> bookingService) {
        Set<BookingServiceDetail> bookingServiceDetails =
                new LinkedHashSet<>(Math.max((int) (bookingService.size() / .75f) + 1, 16));
        // services ordered by the customer
        List<Service> services = getServicesFromBookingServiceDTOs(bookingService);
        for (BookingServiceDTO bookingServiceDTO : bookingService) {
            BookingServiceDetail serviceDetail = new BookingServiceDetail();
            for (Service service : services) {
                if (service.getId().equals(bookingServiceDTO.getServiceId())) {
                    serviceDetail.setPrice(service.getPrice());
                    serviceDetail.setAmount(bookingServiceDTO.getAmount());
                    serviceDetail.setBooking(booking);
                    serviceDetail.setService(service);
                }
            }
            bookingServiceDetailRepository.save(serviceDetail);
            bookingServiceDetails.add(serviceDetail);
        }
        return bookingServiceDetails;
    }

    private List<Service> getServicesFromBookingServiceDTOs(List<BookingServiceDTO> bookingServiceDTOs) {
        List<Service> services = new LinkedList<>();
        for (BookingServiceDTO bookingServiceDTO : bookingServiceDTOs) {
            Optional<Service> service = serviceRepository.findById(bookingServiceDTO.getServiceId());
            checkNotFoundServiceById(service, bookingServiceDTO.getServiceId());
            services.add(service.get());
        }
        return services;
    }

    private void checkNotFoundServiceById(Optional<Service> service, Long serviceId) {
        if (service.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, serviceId));
        }
    }

}
