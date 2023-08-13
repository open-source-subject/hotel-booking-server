package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.pagination.PaginationRequestDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class MediaController {

    private final MediaService mediaService;

    @Tag(name = "media-controller-admin")
    @Operation(summary = "API get all media in trash")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Media.GET_POSTS_IN_TRASH)
    public ResponseEntity<?> getMediasInTrash(@Valid @ParameterObject PaginationRequestDTO requestDTO) {
        return VsResponseUtil.ok(mediaService.getMediasInTrash(requestDTO, CommonConstant.TRUE));
    }

    @Tag(name = "media-controller-admin")
    @Operation(summary = "API delete media permanently by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @DeleteMapping(UrlConstant.Media.DELETE_MEDIA_PERMANENTLY)
    public ResponseEntity<?> deleteMediaPermanentlyById(@PathVariable Long mediaId) {
        return VsResponseUtil.ok(mediaService.deleteMediaPermanently(mediaId));
    }

    @Tag(name = "media-controller-admin")
    @Operation(summary = "API restore media by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.Media.RESTORE_MEDIA)
    public ResponseEntity<?> restoreMediaById(@PathVariable Long mediaId) {
        return VsResponseUtil.ok(mediaService.restoreMedia(mediaId));
    }

}
