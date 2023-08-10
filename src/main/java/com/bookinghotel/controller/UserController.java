package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.UserUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.security.CurrentUserLogin;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

    private final UserService userService;

    @Tag(name = "user-controller-admin")
    @Operation(summary = "API get user")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.User.GET_USER)
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return VsResponseUtil.ok(userService.getUserById(userId));
    }

    @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
    @Operation(summary = "API get current user login")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true) @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(userService.getCurrentUser(principal));
    }

    @Tag(name = "user-controller-admin")
    @Operation(summary = "API get all customer")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.User.GET_USERS)
    public ResponseEntity<?> getCustomers(@Valid @ParameterObject PaginationSearchSortRequestDTO requestDTO,
                                          @RequestParam Boolean isLocked) {
        return VsResponseUtil.ok(userService.getCustomers(requestDTO, isLocked));
    }

    @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
    @Operation(summary = "API update user by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @PatchMapping(value = UrlConstant.User.UPDATE_USER, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserById(@PathVariable String userId,
                                            @Valid @ModelAttribute UserUpdateDTO userUpdateDTO,
                                            @Parameter(name = "principal", hidden = true) @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(userService.updateUser(userUpdateDTO, userId, principal));
    }

    @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
    @Operation(summary = "API change password")
    @AuthorizationInfo(role = {RoleConstant.ADMIN, RoleConstant.USER})
    @PatchMapping(value = UrlConstant.User.CHANGE_PASS)
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                            @Parameter(name = "principal", hidden = true) @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(userService.changePassword(oldPassword, newPassword, principal));
    }

    @Tag(name = "user-controller-admin")
    @Operation(summary = "API lock and unlock user by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.User.LOCK_UNLOCK_USER)
    public ResponseEntity<?> lockAndUnlockUser(@PathVariable String userId, @RequestParam Boolean isLocked) {
        if (isLocked) {
            return VsResponseUtil.ok(userService.lockUser(userId));
        } else {
            return VsResponseUtil.ok(userService.unlockUser(userId));
        }
    }

    @Tag(name = "user-controller-admin")
    @Operation(summary = "API delete user by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @DeleteMapping(UrlConstant.User.DELETE_USER)
    public ResponseEntity<?> deleteUserById(@PathVariable String userId) {
        return VsResponseUtil.ok(userService.deleteUserPermanently(userId));
    }

}
