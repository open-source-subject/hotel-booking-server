package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSummaryDTO {

    private String id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String avatar;

}

