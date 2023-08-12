package com.bookinghotel.service;

import com.bookinghotel.dto.AuthenticationRequestDTO;
import com.bookinghotel.dto.AuthenticationResponseDTO;
import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthenticationResponseDTO login(AuthenticationRequestDTO request);

    CommonResponseDTO signUp(UserCreateDTO userCreateDTO);

    CommonResponseDTO verifySignUp(String email, String token);

    CommonResponseDTO forgotPassword(String email);

    CommonResponseDTO verifyForgotPassword(String email, String token, String newPassword);

    CommonResponseDTO logout(HttpServletRequest request);

}
