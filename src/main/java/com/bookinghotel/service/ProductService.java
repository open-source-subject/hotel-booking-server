package com.bookinghotel.service;

import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface ProductService {

    ProductDTO getProduct(Long productId);

    PaginationResponseDTO<ProductDTO> getProducts(PaginationSearchSortRequestDTO requestDTO, Boolean deleteFlag);

    PaginationResponseDTO<ProductDTO> getProductsByServiceId(Long serviceId, PaginationSearchSortRequestDTO requestDTO);

    ProductDTO createProduct(ProductCreateDTO productCreateDTO, UserPrincipal principal);

    ProductDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO, UserPrincipal principal);

    CommonResponseDTO deleteProduct(Long productId);

    CommonResponseDTO deleteProductPermanently(Long productId);

    CommonResponseDTO restoreProduct(Long productId);

    void deleteProductByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords);

}
