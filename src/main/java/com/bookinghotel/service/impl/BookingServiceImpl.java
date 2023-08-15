package com.bookinghotel.service.impl;

import com.bookinghotel.config.UserInfoProperties;
import com.bookinghotel.constant.*;
import com.bookinghotel.dto.*;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.common.DataMailDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Booking;
import com.bookinghotel.entity.BookingRoomDetail;
import com.bookinghotel.entity.BookingServiceDetail;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.BookingMapper;
import com.bookinghotel.mapper.UserMapper;
import com.bookinghotel.repository.BookingRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.repository.projection.BookingProjection;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.BookingRoomDetailService;
import com.bookinghotel.service.BookingService;
import com.bookinghotel.service.BookingServiceDetailService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.GrantedAuthority;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final BookingRoomDetailService bookingRoomDetailService;

    private final BookingServiceDetailService bookingServiceDetailService;

    private final BookingMapper bookingMapper;

    private final UserMapper userMapper;

    private final SendMailUtil sendMail;

    private final UserInfoProperties userInfoProperties;

    @Qualifier("threadPoolTaskExecutorHotelBooking")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public BookingDTO getBookingById(Long bookingId) {
        BookingProjection booking = bookingRepository.findBookingProjectionById(bookingId);
        checkNotFoundBookingById(booking, bookingId);
        return mapperToBookingDTO(booking);
    }

    @Override
    public PaginationResponseDTO<BookingDTO> getBookingsForUser(PaginationSortRequestDTO requestDTO,
                                                                UserPrincipal principal) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.BOOKING);
        Page<BookingProjection> bookings = bookingRepository.findAllForUser(principal.getId(), pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.BOOKING, bookings);
        List<BookingDTO> bookingDTOs = mapperToBookingDTOs(bookings.getContent());
        return new PaginationResponseDTO<>(meta, bookingDTOs);
    }

    @Override
    public PaginationResponseDTO<BookingDTO> getBookingsForAdmin(PaginationSortRequestDTO requestDTO,
                                                                 BookingFilterDTO filterDTO) {
        String bookingStatus = filterDTO.getBookingStatus() == null ? null : filterDTO.getBookingStatus().toString();
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.BOOKING);
        Page<BookingProjection> bookings = bookingRepository.findAllForAdmin(filterDTO, bookingStatus, pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.BOOKING, bookings);
        List<BookingDTO> bookingDTOs = mapperToBookingDTOs(bookings.getContent());
        return new PaginationResponseDTO<>(meta, bookingDTOs);
    }

    @Override
    @Transactional
    public BookingDTO createBooking(BookingCreateDTO bookingCreateDTO, UserPrincipal principal) {
        LocalDate now = LocalDate.now();
        checkExpectedCheckIn(now, bookingCreateDTO.getExpectedCheckIn(), bookingCreateDTO.getExpectedCheckOut());
        checkExpectedCheckOut(now, bookingCreateDTO.getExpectedCheckIn(), bookingCreateDTO.getExpectedCheckOut());
        User creator = userRepository.getUser(principal);
        Booking booking = new Booking();
        booking.setExpectedCheckIn(bookingCreateDTO.getExpectedCheckIn());
        booking.setExpectedCheckOut(bookingCreateDTO.getExpectedCheckOut());
        booking.setStatus(BookingStatus.PENDING);
        booking.setUser(creator);
        bookingRepository.save(booking);
        //mapper to output
        Booking bookingCreated = bookingMapper.toBookingCreated(booking);
        Set<BookingRoomDetail> bookingRoomDetails = bookingRoomDetailService.createBookingRoomDetails(booking,
                bookingCreateDTO.getRoomIds());
        bookingCreated.setBookingRoomDetails(bookingRoomDetails);
        if (!bookingCreateDTO.getServices().isEmpty()) {
            Set<BookingServiceDetail> bookingServiceDetails =
                    bookingServiceDetailService.createBookingServiceDetails(booking, bookingCreateDTO.getServices());
            bookingCreated.setBookingServiceDetails(bookingServiceDetails);
        }
        return mapperToBookingDTO(bookingCreated, creator, creator);
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, BookingUpdateDTO bookingUpdateDTO, UserPrincipal principal) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        checkNotFoundBookingById(booking, bookingId);
        bookingMapper.updateBookingFromDTO(bookingUpdateDTO, booking.get());
        bookingRepository.save(booking.get());
        Optional<User> creator = userRepository.findById(booking.get().getCreatedBy());
        User updater = userRepository.getUser(principal);
        return mapperToBookingDTO(booking.get(), creator.get(), updater);
    }

    @Override
    public BookingDTO checkIn(Long bookingId, UserPrincipal principal) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        checkNotFoundBookingById(booking, bookingId);
        LocalDateTime now = LocalDateTime.now();
        booking.get().setCheckIn(now);
        booking.get().setStatus(BookingStatus.CHECKED_IN);
        bookingRepository.save(booking.get());
        Optional<User> creator = userRepository.findById(booking.get().getCreatedBy());
        User updater = userRepository.getUser(principal);
        return mapperToBookingDTO(booking.get(), creator.get(), updater);
    }

    @Override
    public BookingDTO checkOutAndPayment(Long bookingId, UserPrincipal principal) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        checkNotFoundBookingById(booking, bookingId);
        if (booking.get().getCheckIn() == null) {
            throw new InvalidException(ErrorMessage.Booking.ERR_NOT_CHECKIN);
        }
        LocalDateTime now = LocalDateTime.now();
        booking.get().setCheckOut(now);
        booking.get().setStatus(BookingStatus.CHECKED_OUT);
        bookingRepository.save(booking.get());
        Optional<User> creator = userRepository.findById(booking.get().getCreatedBy());
        User updater = userRepository.getUser(principal);
        return mapperToBookingDTO(booking.get(), creator.get(), updater);
    }

    @Override
    public CommonResponseDTO cancelBooking(Long bookingId, String note, UserPrincipal principal) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        checkNotFoundBookingById(booking, bookingId);
        checkPermissionCancelBooking(booking.get(), principal);
        LocalDateTime now = LocalDateTime.now();
        long totalHours = ChronoUnit.HOURS.between(booking.get().getExpectedCheckIn(), now);
        if (totalHours > CommonConstant.HOURS_IN_A_DAY) {
            throw new InvalidException(ErrorMessage.Booking.ERR_CAN_NOT_CANCEL_BOOKING);
        }
        booking.get().setStatus(BookingStatus.CANCEL);
        booking.get().setNote(note);
        bookingRepository.save(booking.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.CANCEL_SUCCESS);
    }

    @Override
    public void lockUserRefuseToCheckIn() {
        LocalDateTime now = LocalDateTime.now();
        //set data mail
        DataMailDTO dataMailDTO = new DataMailDTO();
        dataMailDTO.setSubject(CommonMessage.SUBJECT_ACCOUNT_LOCK_NOTICE);
        List<Booking> bookings = bookingRepository.findBookingUserByStatus(BookingStatus.PENDING.toString());
        for (Booking booking : bookings) {
            LocalDateTime expectedCheckIn = booking.getExpectedCheckIn().plusHours(CommonConstant.LATE_CHECKIN_HOURS);
            if (expectedCheckIn.isBefore(now)) {
                threadPoolTaskExecutor.execute(() -> {
                    User userBooking = booking.getUser();
                    userBooking.setIsLocked(CommonConstant.TRUE);
                    userRepository.save(userBooking);
                    booking.setStatus(BookingStatus.CANCEL);
                    booking.setNote("Customer refuse to check in");
                    bookingRepository.save(booking);
                    //set data mail
                    dataMailDTO.setTo(userBooking.getEmail());
                    Map<String, Object> properties = new HashMap<>();
                    properties.put("name", userBooking.getLastName() + " " + userBooking.getFirstName());
                    properties.put("hotline", userInfoProperties.getHotline());
                    dataMailDTO.setProperties(properties);
                    try {
                        sendMail.sendEmailWithHTML(dataMailDTO, CommonMessage.ACCOUNT_LOCK_NOTICE_TEMPLATE);
                        log.info(String.format("Successfully locked account %s refusal to check in", userBooking.getEmail()));
                    } catch (Exception ex) {
                        throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
                    }
                });
            }
        }
    }

    @Override
    public List<BookingDTO> mapperToBookingDTOs(List<BookingProjection> bookings) {
        List<BookingDTO> bookingDTOs = new LinkedList<>();
        for (BookingProjection booking : bookings) {
            BookingDTO bookingDTO = mapperToBookingDTO(booking);
            bookingDTOs.add(bookingDTO);
        }
        return bookingDTOs;
    }

    private BookingDTO mapperToBookingDTO(BookingProjection booking) {
        Set<BookingRoomDetail> bookingRoomDetails =
                bookingRoomDetailService.getBookingRoomDetailsByBooking(booking.getId());
        Set<BookingServiceDetail> bookingServiceDetails =
                bookingServiceDetailService.getBookingServiceDetailsByBooking(booking.getId());
        BookingDTO bookingDTO = bookingMapper.toBookingDTO(booking);
        //check booking status to calculate surcharge
        if (booking.getStatus().equals(BookingStatus.CHECKED_IN) || booking.getStatus().equals(BookingStatus.CHECKED_OUT)) {
            bookingDTO.setSurcharges(calculateSurcharge(booking, bookingRoomDetails));
        }
        //room
        bookingDTO.setTotalRoomPrice(calculateTotalRoomPrice(booking, bookingRoomDetails));
        List<BookingRoomDetailDTO> bookingRoomDetailDTOs = bookingMapper.toBookingRoomDetailDTOs(bookingRoomDetails);
        bookingDTO.setRooms(bookingRoomDetailDTOs);
        //service
        bookingDTO.setTotalServicePrice(calculateTotalServicePrice(booking, bookingServiceDetails));
        List<BookingServiceDetailDTO> bookingServiceDetailDTOs =
                bookingMapper.toBookingServiceDetailDTOs(bookingServiceDetails);
        bookingDTO.setServices(bookingServiceDetailDTOs);
        return bookingDTO;
    }

    private BookingDTO mapperToBookingDTO(Booking booking, User creator, User updater) {
        Set<BookingRoomDetail> bookingRoomDetails = booking.getBookingRoomDetails();
        Set<BookingServiceDetail> bookingServiceDetails = booking.getBookingServiceDetails();
        BookingDTO bookingDTO = bookingMapper.toBookingDTO(booking);
        bookingDTO.setCreatedBy(userMapper.toCreatedByDTO(creator));
        bookingDTO.setLastModifiedBy(userMapper.toLastModifiedByDTO(updater));
        //check booking status to calculate surcharge
        if (booking.getStatus().equals(BookingStatus.CHECKED_IN) || booking.getStatus().equals(BookingStatus.CHECKED_OUT)) {
            bookingDTO.setSurcharges(calculateSurcharge(booking, bookingRoomDetails));
        }
        //room
        bookingDTO.setTotalRoomPrice(calculateTotalRoomPrice(booking, bookingRoomDetails));
        List<BookingRoomDetailDTO> bookingRoomDetailDTOs = bookingMapper.toBookingRoomDetailDTOs(bookingRoomDetails);
        bookingDTO.setRooms(bookingRoomDetailDTOs);
        //service
        bookingDTO.setTotalServicePrice(calculateTotalServicePrice(booking, bookingServiceDetails));
        List<BookingServiceDetailDTO> bookingServiceDetailDTOs =
                bookingMapper.toBookingServiceDetailDTOs(bookingServiceDetails);
        bookingDTO.setServices(bookingServiceDetailDTOs);
        return bookingDTO;
    }

    @Override
    public Long calculateTotalRoomPrice(BookingProjection booking, Set<BookingRoomDetail> bookingRoomDetails) {
        long totalRoomPrice = 0L;
        Long totalDay = Math.round(ChronoUnit.HOURS.between(booking.getExpectedCheckIn(),
                booking.getExpectedCheckOut()) / 24d);
        for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
            if (bookingRoomDetail.getSalePercent() != null) {
                double salePrice = bookingRoomDetail.getPrice() * (bookingRoomDetail.getSalePercent() / 100f);
                totalRoomPrice += (bookingRoomDetail.getPrice() - salePrice) * totalDay;
            } else {
                totalRoomPrice += bookingRoomDetail.getPrice() * totalDay;
            }
        }
        return totalRoomPrice;
    }

    @Override
    public Long calculateTotalRoomPrice(Booking booking, Set<BookingRoomDetail> bookingRoomDetails) {
        long totalRoomPrice = 0L;
        Long totalDay = Math.round(ChronoUnit.HOURS.between(booking.getExpectedCheckIn(),
                booking.getExpectedCheckOut()) / 24d);
        for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
            if (bookingRoomDetail.getSalePercent() != null) {
                double salePrice = bookingRoomDetail.getPrice() * (bookingRoomDetail.getSalePercent() / 100f);
                totalRoomPrice += (bookingRoomDetail.getPrice() - salePrice) * totalDay;
            } else {
                totalRoomPrice += bookingRoomDetail.getPrice() * totalDay;
            }
        }
        return totalRoomPrice;
    }

    @Override
    public Long calculateTotalServicePrice(BookingProjection booking, Set<BookingServiceDetail> bookingServiceDetails) {
        long totalServicePrice = 0L;
        for (BookingServiceDetail bookingServiceDetail : bookingServiceDetails) {
            totalServicePrice += (bookingServiceDetail.getPrice() * bookingServiceDetail.getAmount());
        }
        return totalServicePrice;
    }

    @Override
    public Long calculateTotalServicePrice(Booking booking, Set<BookingServiceDetail> bookingServiceDetails) {
        long totalServicePrice = 0L;
        for (BookingServiceDetail bookingServiceDetail : bookingServiceDetails) {
            totalServicePrice += (bookingServiceDetail.getPrice() * bookingServiceDetail.getAmount());
        }
        return totalServicePrice;
    }

    @Override
    public List<BookingSurchargeDTO> calculateSurcharge(BookingProjection booking,
                                                        Set<BookingRoomDetail> bookingRoomDetails) {
        List<BookingSurchargeDTO> roomSurcharges = new LinkedList<>();
        if (booking.getCheckIn() != null && !booking.getCheckIn().equals(LocalDateTime.MIN)) {
            roomSurcharges.add(calculateTotalCheckInSurcharge(booking, bookingRoomDetails));
        }
        if (booking.getCheckOut() != null && !booking.getCheckOut().equals(LocalDateTime.MIN)) {
            roomSurcharges.add(calculateTotalCheckOutSurcharge(booking, bookingRoomDetails));
        }
        return roomSurcharges;
    }

    @Override
    public List<BookingSurchargeDTO> calculateSurcharge(Booking booking, Set<BookingRoomDetail> bookingRoomDetails) {
        List<BookingSurchargeDTO> roomSurcharges = new LinkedList<>();
        if (booking.getCheckIn() != null && !booking.getCheckIn().equals(LocalDateTime.MIN)) {
            roomSurcharges.add(calculateTotalCheckInSurcharge(booking, bookingRoomDetails));
        }
        if (booking.getCheckOut() != null && !booking.getCheckOut().equals(LocalDateTime.MIN)) {
            roomSurcharges.add(calculateTotalCheckOutSurcharge(booking, bookingRoomDetails));
        }
        return roomSurcharges;
    }

    private BookingSurchargeDTO calculateTotalCheckInSurcharge(BookingProjection booking,
                                                               Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDateTime checkIn = booking.getCheckIn();
        LocalDate dateCheckIn = checkIn.toLocalDate();
        LocalDateTime date5h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_5H00);
        LocalDateTime date9h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_9H00);
        LocalDateTime date14h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_14H00);
        BookingSurchargeDTO checkInSurcharge = new BookingSurchargeDTO();
        long totalCheckInSurcharge = 0L;
        if (checkIn.isBefore(date5h)) {
            checkInSurcharge.setReason("You check-in before 5h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += bookingRoomDetail.getPrice();
            }
        } else if (checkIn.isAfter(date5h) && checkIn.isBefore(date9h)) {
            checkInSurcharge.setReason("You check-in from 5h to 9h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkIn.isAfter(date9h) && checkIn.isBefore(date14h)) {
            checkInSurcharge.setReason("You check-in from 9h to 14h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else {
            return null;
        }
        checkInSurcharge.setRoomSurcharge(totalCheckInSurcharge);
        return checkInSurcharge;
    }

    private BookingSurchargeDTO calculateTotalCheckInSurcharge(Booking booking,
                                                               Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDateTime checkIn = booking.getCheckIn();
        LocalDate dateCheckIn = checkIn.toLocalDate();
        LocalDateTime date5h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_5H00);
        LocalDateTime date9h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_9H00);
        LocalDateTime date14h = LocalDateTime.of(dateCheckIn, CommonConstant.TIME_14H00);
        BookingSurchargeDTO checkInSurcharge = new BookingSurchargeDTO();
        long totalCheckInSurcharge = 0L;
        if (checkIn.isBefore(date5h)) {
            checkInSurcharge.setReason("You check-in before 5h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += bookingRoomDetail.getPrice();
            }
        } else if (checkIn.isAfter(date5h) && checkIn.isBefore(date9h)) {
            checkInSurcharge.setReason("You check-in from 5h to 9h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkIn.isAfter(date9h) && checkIn.isBefore(date14h)) {
            checkInSurcharge.setReason("You check-in from 9h to 14h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckInSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else {
            return null;
        }
        checkInSurcharge.setRoomSurcharge(totalCheckInSurcharge);
        return checkInSurcharge;
    }

    private BookingSurchargeDTO calculateTotalCheckOutSurcharge(BookingProjection booking,
                                                                Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDateTime checkOut = booking.getCheckOut();
        LocalDate dateCheckOut = checkOut.toLocalDate();
        LocalDateTime date12h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_12H00);
        LocalDateTime date15h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_15H00);
        LocalDateTime date18h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_18H00);
        BookingSurchargeDTO checkOutSurcharge = new BookingSurchargeDTO();
        long totalCheckOutSurcharge = 0L;
        if (checkOut.isAfter(date12h) && checkOut.isBefore(date15h)) {
            checkOutSurcharge.setReason("You check-out from 12h to 15h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else if (checkOut.isAfter(date15h) && checkOut.isBefore(date18h)) {
            checkOutSurcharge.setReason("You check-out from 15h to 18h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkOut.isAfter(date18h)) {
            checkOutSurcharge.setReason("You check-out after 18h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += bookingRoomDetail.getPrice();
            }
        } else {
            return null;
        }
        checkOutSurcharge.setRoomSurcharge(totalCheckOutSurcharge);
        return checkOutSurcharge;
    }

    private BookingSurchargeDTO calculateTotalCheckOutSurcharge(Booking booking,
                                                                Set<BookingRoomDetail> bookingRoomDetails) {
        LocalDateTime checkOut = booking.getCheckOut();
        LocalDate dateCheckOut = checkOut.toLocalDate();
        LocalDateTime date_12h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_12H00);
        LocalDateTime date_15h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_15H00);
        LocalDateTime date_18h = LocalDateTime.of(dateCheckOut, CommonConstant.TIME_18H00);
        BookingSurchargeDTO checkOutSurcharge = new BookingSurchargeDTO();
        long totalCheckOutSurcharge = 0L;
        if (checkOut.isAfter(date_12h) && checkOut.isBefore(date_15h)) {
            checkOutSurcharge.setReason("You check-out from 12h to 15h. You pay 30% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.3);
            }
        } else if (checkOut.isAfter(date_15h) && checkOut.isBefore(date_18h)) {
            checkOutSurcharge.setReason("You check-out from 15h to 18h. You pay 50% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += Math.round(bookingRoomDetail.getPrice() * 0.5);
            }
        } else if (checkOut.isAfter(date_18h)) {
            checkOutSurcharge.setReason("You check-out after 18h. You pay 100% more of the total room price");
            for (BookingRoomDetail bookingRoomDetail : bookingRoomDetails) {
                totalCheckOutSurcharge += bookingRoomDetail.getPrice();
            }
        } else {
            return null;
        }
        checkOutSurcharge.setRoomSurcharge(totalCheckOutSurcharge);
        return checkOutSurcharge;
    }

    private void checkExpectedCheckIn(LocalDate now, LocalDateTime expectedCheckIn, LocalDateTime expectedCheckOut) {
        LocalDate checkInLocalDate = expectedCheckIn.toLocalDate();
        LocalTime checkInLocalTime = expectedCheckIn.toLocalTime();
        LocalDate checkOutLocalDate = expectedCheckOut.toLocalDate();
        if (checkInLocalDate.isAfter(checkOutLocalDate) || checkInLocalDate.equals(checkOutLocalDate)
                || checkInLocalDate.isBefore(now) || !checkInLocalTime.equals(CommonConstant.TIME_14H00)) {
            throw new InvalidException(ErrorMessage.INVALID_DATE_CHECK_IN);
        }
    }

    private void checkExpectedCheckOut(LocalDate now, LocalDateTime expectedCheckIn, LocalDateTime expectedCheckOut) {
        LocalDate checkInLocalDate = expectedCheckIn.toLocalDate();
        LocalDate checkOutLocalDate = expectedCheckOut.toLocalDate();
        LocalTime checkOutLocalTime = expectedCheckOut.toLocalTime();
        if (checkOutLocalDate.isBefore(checkInLocalDate) || checkOutLocalDate.equals(checkInLocalDate)
                || checkOutLocalDate.isBefore(now) || checkOutLocalDate.equals(now)
                || !checkOutLocalTime.equals(CommonConstant.TIME_12H00)) {
            throw new InvalidException(ErrorMessage.INVALID_DATE_CHECK_OUT);
        }
    }

    private void checkPermissionCancelBooking(Booking booking, UserPrincipal currentUser) {
        if (!booking.getUser().getId().equals(currentUser.getId())) {
            for (GrantedAuthority authority : currentUser.getAuthorities()) {
                if (!authority.getAuthority().equals(RoleConstant.ADMIN)) {
                    throw new InvalidException(ErrorMessage.Booking.ERR_NO_PERMISSION_TO_CANCEL_BOOKING);
                }
            }
        }
    }

    private void checkNotFoundBookingById(Optional<Booking> booking, Long bookingId) {
        if (booking.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Booking.ERR_NOT_FOUND_ID, bookingId));
        }
    }

    private void checkNotFoundBookingById(BookingProjection booking, Long bookingId) {
        if (ObjectUtils.isEmpty(booking)) {
            throw new NotFoundException(String.format(ErrorMessage.Booking.ERR_NOT_FOUND_ID, bookingId));
        }
    }

}
