package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Product;
import com.bookinghotel.entity.Service;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.ProductMapper;
import com.bookinghotel.repository.ProductRepository;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.repository.projection.ProductProjection;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.ProductService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ServiceRepository serviceRepository;

    private final UserRepository userRepository;

    private final ProductMapper productMapper;

    private final UploadFileUtil uploadFile;

    @Override
    public ProductDTO getProduct(Long productId) {
        ProductProjection product = productRepository.findProductById(productId);
        checkNotFoundProductById(product, productId);
        return productMapper.productProjectionToProductDTO(product);
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getProducts(PaginationSearchSortRequestDTO requestDTO, Boolean deleteFlag) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.PRODUCT);
        Page<ProductProjection> products = productRepository.findAllProduct(requestDTO.getKeyword(), deleteFlag,
                pageable);
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.PRODUCT, products);
        return new PaginationResponseDTO<ProductDTO>(meta, toProductDTOs(products));
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getProductsByServiceId(Long serviceId, PaginationSearchSortRequestDTO requestDTO) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.PRODUCT);
        Page<ProductProjection> products = productRepository.findAllByServiceId(pageable, serviceId,
                requestDTO.getKeyword());
        //Create Output
        PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.PRODUCT, products);
        return new PaginationResponseDTO<ProductDTO>(meta, toProductDTOs(products));
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO, UserPrincipal principal) {
        User creator = userRepository.getUser(principal);
        Optional<Service> service = serviceRepository.findById(productCreateDTO.getServiceId());
        checkNotFoundServiceById(service, productCreateDTO.getServiceId());
        Product product = productMapper.createDtoToProduct(productCreateDTO);
        product.setThumbnail(uploadFile.uploadFile(productCreateDTO.getThumbnailFile()));
        product.setService(service.get());
        return productMapper.toProductDTO(productRepository.save(product), creator, creator);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO, UserPrincipal principal) {
        Optional<Product> currentProduct = productRepository.findById(productId);
        checkNotFoundProductById(currentProduct, productId);
        productMapper.updateProductFromDTO(productUpdateDTO, currentProduct.get());
        //update thumbnail
        if (ObjectUtils.isEmpty(productUpdateDTO.getThumbnail())) {
            if (productUpdateDTO.getThumbnailFile() != null) {
                uploadFile.destroyFileWithUrl(currentProduct.get().getThumbnail());
                currentProduct.get().setThumbnail(uploadFile.uploadFile(productUpdateDTO.getThumbnailFile()));
            } else {
                throw new InvalidException(ErrorMessage.Product.ERR_PRODUCT_MUST_HAVE_THUMBNAIL);
            }
        }
        User updater = userRepository.getUser(principal);
        User creator = userRepository.findById(currentProduct.get().getCreatedBy()).get();
        return productMapper.toProductDTO(productRepository.save(currentProduct.get()), creator, updater);
    }

    @Override
    public CommonResponseDTO deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        checkNotFoundProductById(product, productId);
        product.get().setDeleteFlag(CommonConstant.TRUE);
        productRepository.save(product.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO deleteProductPermanently(Long productId) {
        Optional<Product> product = productRepository.findByIdAndIsDeleteFlag(productId);
        checkNotFoundProductIsDeleteFlagById(product, productId);
        productRepository.delete(product.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    }

    @Override
    public CommonResponseDTO restoreProduct(Long productId) {
        Optional<Product> product = productRepository.findByIdAndIsDeleteFlag(productId);
        checkNotFoundProductIsDeleteFlagById(product, productId);
        product.get().setDeleteFlag(CommonConstant.FALSE);
        productRepository.save(product.get());
        return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.RESTORE_SUCCESS);
    }

    @Override
    public void deleteProductByDeleteFlag(Boolean isDeleteFlag, Integer daysToDeleteRecords) {
        productRepository.deleteByDeleteFlag(isDeleteFlag, daysToDeleteRecords);
    }

    private List<ProductDTO> toProductDTOs(Page<ProductProjection> productProjections) {
        List<ProductDTO> productDTOs = new LinkedList<>();
        for (ProductProjection productProjection : productProjections) {
            productDTOs.add(productMapper.productProjectionToProductDTO(productProjection));
        }
        return productDTOs;
    }

    private void checkNotFoundProductById(Optional<Product> product, Long productId) {
        if (product.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Product.ERR_NOT_FOUND_ID, productId));
        }
    }

    private void checkNotFoundProductIsDeleteFlagById(Optional<Product> product, Long productId) {
        if (product.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Product.ERR_NOT_FOUND_ID_IN_TRASH, productId));
        }
    }

    private void checkNotFoundProductById(ProductProjection productProjection, Long productId) {
        if (ObjectUtils.isEmpty(productProjection)) {
            throw new NotFoundException(String.format(ErrorMessage.Product.ERR_NOT_FOUND_ID, productId));
        }
    }

    private void checkNotFoundServiceById(Optional<Service> service, Long serviceId) {
        if (service.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, serviceId));
        }
    }


}
