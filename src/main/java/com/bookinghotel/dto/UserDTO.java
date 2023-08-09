package com.bookinghotel.dto;

import com.bookinghotel.dto.common.DateAuditingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends DateAuditingDTO {

    private String id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthday;

    private String address;

    private String avatar;

    private String roleName;

    private Boolean isEnable;

    private Boolean isLocked;

}

