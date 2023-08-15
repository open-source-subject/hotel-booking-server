package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoleConstant;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.SaleCreateDTO;
import com.bookinghotel.dto.SaleUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.security.AuthorizationInfo;
import com.bookinghotel.security.CurrentUserLogin;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tags(@Tag(name = "sale-controller-admin"))
@RequiredArgsConstructor
@RestApiV1
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "API get sale by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Sale.GET_SALE)
    public ResponseEntity<?> getSaleById(@PathVariable Long saleId) {
        return VsResponseUtil.ok(saleService.getSale(saleId));
    }

    @Operation(summary = "API get all sale")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @GetMapping(UrlConstant.Sale.GET_SALES)
    public ResponseEntity<?> getSales(@Valid @ParameterObject PaginationSearchSortRequestDTO requestDTO,
                                      @RequestParam Boolean deleteFlag) {
        return VsResponseUtil.ok(saleService.getSales(requestDTO, deleteFlag));
    }

    @Operation(summary = "API create sale")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.Sale.CREATE_SALE)
    public ResponseEntity<?> createSale(@Valid @RequestBody SaleCreateDTO saleCreateDTO,
                                        @Parameter(name = "principal", hidden = true)
                                        @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(saleService.createSale(saleCreateDTO, principal));
    }

    @Operation(summary = "API update sale by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PutMapping(UrlConstant.Sale.UPDATE_SALE)
    public ResponseEntity<?> updateSaleById(@PathVariable Long saleId, @Valid @RequestBody SaleUpdateDTO saleUpdateDTO,
                                            @Parameter(name = "principal", hidden = true)
                                            @CurrentUserLogin UserPrincipal principal) {
        return VsResponseUtil.ok(saleService.updateSale(saleId, saleUpdateDTO, principal));
    }

    @Operation(summary = "API add sale to room")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.Sale.ADD_SALE_TO_ROOM)
    public ResponseEntity<?> addSaleToRooms(@PathVariable Long saleId, @RequestBody List<Long> roomIds) {
        return VsResponseUtil.ok(saleService.addSalesToRooms(saleId, roomIds));
    }

    @Operation(summary = "API remove sale from rooms")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.Sale.REMOVE_SALE_FROM_ROOMS)
    public ResponseEntity<?> removeSaleFromRooms(@RequestBody List<Long> roomIds) {
        return VsResponseUtil.ok(saleService.removeSaleFromRooms(roomIds));
    }

    @Operation(summary = "API delete sale by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @DeleteMapping(UrlConstant.Sale.DELETE_SALE)
    public ResponseEntity<?> deleteSaleById(@PathVariable Long saleId) {
        return VsResponseUtil.ok(saleService.deleteSale(saleId));
    }

    @Operation(summary = "API delete sale permanently by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @DeleteMapping(UrlConstant.Sale.DELETE_SALE_PERMANENTLY)
    public ResponseEntity<?> deleteSalePermanentlyById(@PathVariable Long saleId) {
        return VsResponseUtil.ok(saleService.deleteSalePermanently(saleId));
    }

    @Operation(summary = "API restore sale by id")
    @AuthorizationInfo(role = {RoleConstant.ADMIN})
    @PostMapping(UrlConstant.Sale.RESTORE_SALE)
    public ResponseEntity<?> restoreSaleById(@PathVariable Long saleId) {
        return VsResponseUtil.ok(saleService.restoreSale(saleId));
    }

}
