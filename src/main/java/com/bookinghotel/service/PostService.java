package com.bookinghotel.service;

import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface PostService {

    PostDTO getPost(Long postId);

    PaginationResponseDTO<PostDTO> getPosts(PaginationSortRequestDTO requestDTO, Boolean deleteFlag);

    PostDTO createPost(PostCreateDTO createDTO, UserPrincipal currentUser);

    PostDTO updatePost(Long postId, PostUpdateDTO updateDTO, UserPrincipal currentUser);

    CommonResponseDTO deletePost(Long postId);

    CommonResponseDTO deletePostPermanently(Long postId);

    CommonResponseDTO restorePost(Long postId);

    void deletePostByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

}
