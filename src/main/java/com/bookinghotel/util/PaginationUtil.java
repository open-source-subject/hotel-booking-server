package com.bookinghotel.util;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.pagination.PaginationRequestDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    private static final String CREATED_DATE = "created_date";

    private static final String LAST_MODIFIED_DATE = "last_modified_date";

    public static Pageable buildPageableSortCreatedDate(PaginationRequestDTO requestDTO) {
        Sort sort = Sort.by(CREATED_DATE).descending();
        return PageRequest.of(requestDTO.getPageNum(), requestDTO.getPageSize(), sort);
    }

    public static Pageable buildPageableSortLastModifiedDate(PaginationRequestDTO requestDTO) {
        Sort sort = Sort.by(LAST_MODIFIED_DATE).descending();
        return PageRequest.of(requestDTO.getPageNum(), requestDTO.getPageSize(), sort);
    }

    public static Pageable buildPageable(PaginationSortRequestDTO requestDTO, SortByDataConstant constant) {
        Sort sort;
        if (requestDTO.getSortType().equalsIgnoreCase(CommonConstant.SORT_TYPE_ASC)) {
            sort = Sort.by(requestDTO.getSortBy(constant)).ascending();
        } else {
            sort = Sort.by(requestDTO.getSortBy(constant)).descending();
        }
        return PageRequest.of(requestDTO.getPageNum(), requestDTO.getPageSize(), sort);
    }

    public static <T> PagingMeta buildPagingMeta(PaginationRequestDTO requestDTO, Page<T> pages) {
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                requestDTO.getPageNum() + CommonConstant.ONE_INT_VALUE,
                requestDTO.getPageSize(),
                CREATED_DATE,
                CommonConstant.SORT_TYPE_DESC
        );
    }

    public static <T> PagingMeta buildPagingMeta(PaginationSortRequestDTO requestDTO, SortByDataConstant constant,
                                                 Page<T> pages) {
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                requestDTO.getPageNum() + CommonConstant.ONE_INT_VALUE,
                requestDTO.getPageSize(),
                requestDTO.getSortBy(constant),
                requestDTO.getSortType()
        );
    }

}
