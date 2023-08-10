package com.bookinghotel.service;

import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.dto.UserDTO;
import com.bookinghotel.dto.UserUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.entity.User;
import com.bookinghotel.security.UserPrincipal;

public interface UserService {

  UserDTO getUserById(String userId);

  UserDTO getCurrentUser(UserPrincipal principal);

  PaginationResponseDTO<UserDTO> getCustomers(PaginationSearchSortRequestDTO requestDTO, Boolean isLocked);

  User createUser(UserCreateDTO userCreateDTO);

  UserDTO updateUser(UserUpdateDTO userUpdateDTO, String userId, UserPrincipal principal);

  CommonResponseDTO changePassword(String oldPassword, String newPassword, UserPrincipal principal);

  CommonResponseDTO lockUser(String userId);

  CommonResponseDTO unlockUser(String userId);

  CommonResponseDTO deleteUserPermanently(String userId);

}
