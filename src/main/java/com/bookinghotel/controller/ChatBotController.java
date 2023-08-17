package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.service.ChatBotService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Hidden
@RequiredArgsConstructor
@RestApiV1
public class ChatBotController {

    private final ChatBotService chatBotService;

    @GetMapping(UrlConstant.ChatBot.GET_ROOM_BY_TYPE)
    public ResponseEntity<?> getRoomByType(@RequestParam String typeRoom) {
        return ResponseEntity.ok(chatBotService.getRoomsByType(typeRoom));
    }

    @GetMapping(UrlConstant.ChatBot.GET_ROOM_BY_ID)
    public ResponseEntity<?> getRoomDetail(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatBotService.getRoomById(roomId));
    }

    @GetMapping(UrlConstant.ChatBot.GET_SERVICES)
    public ResponseEntity<?> getServices() {
        return ResponseEntity.ok(chatBotService.getHotelServices());
    }

    @GetMapping(UrlConstant.ChatBot.GET_SERVICE)
    public ResponseEntity<?> getServiceDetail(@PathVariable Long serviceId) {
        return ResponseEntity.ok(chatBotService.getHotelServiceById(serviceId));
    }

    @GetMapping(UrlConstant.ChatBot.GET_PRODUCT)
    public ResponseEntity<?> getProductDetail(@PathVariable Long productId) {
        return ResponseEntity.ok(chatBotService.getProductsById(productId));
    }

}
