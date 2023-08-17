package com.bookinghotel.repository.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StatisticCustomerTopBookingProjection {

    String getId();

    String getEmail();

    String getPhoneNumber();

    String getFirstName();

    String getLastName();

    String getGender();

    LocalDate getBirthday();

    String getAddress();

    String getAvatar();

    String getRoleName();

    Boolean getIsEnable();

    Boolean getIsLocked();

    LocalDateTime getCreatedDate();

    LocalDateTime getLastModifiedDate();

    Integer getValue();

}
