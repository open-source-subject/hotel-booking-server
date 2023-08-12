package com.bookinghotel.dto;

import com.bookinghotel.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationRequestDTO {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String emailOrPhone;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String password;

}
