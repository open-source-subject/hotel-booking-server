package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.RoomRatingCreateDTO;
import com.bookinghotel.dto.RoomRatingUpdateDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.security.CurrentUserLogin;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.RoomRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class RoomRatingController {

    private final RoomRatingService roomRatingService;

    @Operation(summary = "API get room rating by id")
    @GetMapping(UrlConstant.RoomRating.GET_ROOM_RATING)
    public ResponseEntity<?> getRoomRatingById(@PathVariable Long roomRatingId) {
        return VsResponseUtil.ok(roomRatingService.getRoomRating(roomRatingId));
    }

    @Operation(summary = "API create room rating")
    @AuthorizationInfo(role = {RoleConstant.USER})
    @PostMapping(value = UrlConstant.RoomRating.CREATE_ROOM_RATING)
    public ResponseEntity<?> createRoom(@PathVariable Long roomId,
                                        @Valid @RequestBody RoomRatingCreateDTO roomRatingCreateDTO,
                                        @Parameter(name = "principal", hidden = true)
                                        @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(roomRatingService.createRoomRating(roomId, roomRatingCreateDTO, principal));
    }

    @Operation(summary = "API update room rating by id")
    @AuthorizationInfo(role = {RoleConstant.USER})
    @PutMapping(value = UrlConstant.RoomRating.UPDATE_ROOM_RATING)
    public ResponseEntity<?> updateRoomRatingById(@PathVariable Long roomRatingId,
                                                  @Valid @RequestBody RoomRatingUpdateDTO roomRatingUpdateDTO,
                                                  @Parameter(name = "principal", hidden = true)
                                                  @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(roomRatingService.updateRoomRating(roomRatingId, roomRatingUpdateDTO, principal));
    }

    @Operation(summary = "API delete room rating by id")
    @AuthorizationInfo(role = {RoleConstant.USER, RoleConstant.ADMIN})
    @DeleteMapping(UrlConstant.RoomRating.DELETE_ROOM_RATING)
    public ResponseEntity<?> deleteRoomRatingById(@PathVariable Long roomRatingId,
                                                  @Parameter(name = "principal", hidden = true)
                                                  @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(roomRatingService.deleteRoomRating(roomRatingId, principal));
    }

}
