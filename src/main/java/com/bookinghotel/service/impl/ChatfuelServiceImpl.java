package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.MediaType;
import com.bookinghotel.dto.chatfuel.ChatfuelResponse;
import com.bookinghotel.dto.chatfuel.GalleriesResponse;
import com.bookinghotel.dto.chatfuel.SendImageResponse;
import com.bookinghotel.dto.chatfuel.TextResponse;
import com.bookinghotel.entity.*;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.repository.ProductRepository;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ChatfuelServiceImpl implements ChatBotService {

    private final RoomRepository roomRepository;

    private final ServiceRepository serviceRepository;

    private final ProductRepository productRepository;

    private final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.PATTERN_DATE_TIME);

    @Value("${booking-hotel.server.url}")
    private String serverBaseUrl;

    @Override
    public ChatfuelResponse<Object> getRoomsByType(String typeRoom) {
        List<Room> rooms = roomRepository.findByTypeForChatBot(typeRoom);
        ChatfuelResponse<Object> chatfuelResponse = new ChatfuelResponse<>();
        List<GalleriesResponse.Attachment.Payload.Element> elements = new LinkedList<>();
        for (Room room : rooms) {
            elements.add(setElementRoomByType(room));
        }
        GalleriesResponse galleriesResponse = setGalleriesResponse(elements);
        chatfuelResponse.setMessages(List.of(galleriesResponse));
        return chatfuelResponse;
    }

    @Override
    public ChatfuelResponse<Object> getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID, id)));
        ChatfuelResponse<Object> chatfuelResponse = new ChatfuelResponse<>();
        List<Object> objects = new LinkedList<>(setTextResponseRoom(room));
        Sale sale = room.getSale();
        LocalDateTime now = LocalDateTime.now();
        if (ObjectUtils.isNotEmpty(sale) && sale.getDeleteFlag().equals(CommonConstant.FALSE)
                && sale.getDayStart().isBefore(now) && now.isAfter(sale.getDayEnd())) {
            objects.addAll(setTextResponseSale(sale));
        }
        for (Media media : room.getMedias()) {
            if (objects.size() >= CommonConstant.MAX_MESSAGE_CHAT_BOT) {
                break;
            } else {
                if (media.getType().equals(MediaType.IMAGE)) {
                    objects.add(setSendImageResponse(media.getUrl()));
                }
            }
        }
        chatfuelResponse.setMessages(objects);
        return chatfuelResponse;
    }

    @Override
    public ChatfuelResponse<Object> getHotelServices() {
        List<Service> services = serviceRepository.findAllForChatBot();
        ChatfuelResponse<Object> chatfuelResponse = new ChatfuelResponse<>();
        List<GalleriesResponse.Attachment.Payload.Element> elements = new LinkedList<>();
        for (Service service : services) {
            elements.add(setElementService(service));
        }
        GalleriesResponse galleriesResponse = setGalleriesResponse(elements);
        chatfuelResponse.setMessages(List.of(galleriesResponse));
        return chatfuelResponse;
    }

    @Override
    public ChatfuelResponse<Object> getHotelServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, id)));
        ChatfuelResponse<Object> chatfuelResponse = new ChatfuelResponse<>();
        List<Object> objects = new LinkedList<>(setTextResponseService(service));
        objects.add(setSendImageResponse(service.getThumbnail()));
        objects.add(new TextResponse("Dịch vụ sẽ được đi kèm với các sản phẩm sau:"));
        List<GalleriesResponse.Attachment.Payload.Element> elements = new LinkedList<>();
        for (Product product : service.getProducts()) {
            if (elements.size() >= CommonConstant.MAX_MESSAGE_CHAT_BOT) {
                break;
            } else {
                elements.add(setElementProduct(product));
            }
        }
        GalleriesResponse galleriesResponse = setGalleriesResponse(elements);
        objects.add(galleriesResponse);
        chatfuelResponse.setMessages(objects);
        return chatfuelResponse;
    }

    @Override
    public ChatfuelResponse<Object> getProductsById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessage.Service.ERR_NOT_FOUND_ID, id)));
        ChatfuelResponse<Object> chatfuelResponse = new ChatfuelResponse<>();
        List<Object> objects = new LinkedList<>(setTextResponseProduct(product));
        objects.add(setSendImageResponse(product.getThumbnail()));
        chatfuelResponse.setMessages(objects);
        return chatfuelResponse;
    }

    private GalleriesResponse setGalleriesResponse(List<GalleriesResponse.Attachment.Payload.Element> elements) {
        GalleriesResponse galleriesResponse = new GalleriesResponse();
        GalleriesResponse.Attachment attachment = new GalleriesResponse.Attachment();
        GalleriesResponse.Attachment.Payload payload = new GalleriesResponse.Attachment.Payload();
        payload.setElements(elements);
        attachment.setPayload(payload);
        galleriesResponse.setAttachment(attachment);
        return galleriesResponse;
    }

    private GalleriesResponse.Attachment.Payload.Element setElementRoomByType(Room room) {
        GalleriesResponse.Attachment.Payload.Element element = new GalleriesResponse.Attachment.Payload.Element();
        element.setTitle(room.getName());
        List<Media> media = new LinkedList<>(room.getMedias());
        element.setImage_url(media.get(0).getUrl());
        element.setSubtitle(currencyVN.format(room.getPrice()) + "/đêm");
        GalleriesResponse.Attachment.Payload.Element.Button button =
                new GalleriesResponse.Attachment.Payload.Element.Button();
        button.setTitle("Xem chi tiết");
        button.setType("json_plugin_url");
        button.setUrl(serverBaseUrl + "/chatbot/room/" + room.getId());
        element.setButtons(List.of(button));
        return element;
    }

    private SendImageResponse setSendImageResponse(String url) {
        SendImageResponse imageResponse = new SendImageResponse();
        SendImageResponse.Attachment attachment = new SendImageResponse.Attachment();
        SendImageResponse.Attachment.Payload payload = new SendImageResponse.Attachment.Payload();
        payload.setUrl(url);
        attachment.setPayload(payload);
        imageResponse.setAttachment(attachment);
        return imageResponse;
    }

    private List<TextResponse> setTextResponseRoom(Room room) {
        List<TextResponse> textResponses = new LinkedList<>();
        textResponses.add(new TextResponse(room.getName()));
        textResponses.add(new TextResponse("\uD83D\uDCA5 Giá " + currencyVN.format(room.getPrice()) + "/đêm"));
        textResponses.add(new TextResponse(formatTextRoomResponse(room).toString()));
        return textResponses;
    }

    private StringBuilder formatTextRoomResponse(Room room) {
        StringBuilder sb = new StringBuilder();
        sb.append("✅ Là loại phòng ").append(room.getType()).append("\n");
        sb.append("✅ Phòng có diện tích ").append(room.getSize()).append("m2. ")
                .append("Với ").append(room.getBed()).append(".\n");
        sb.append("✅ Phòng có các dịch vụ sẵn có như: ").append(room.getServices()).append("\n");
        sb.append("✅ ").append(room.getDescription());
        return sb;
    }

    private List<TextResponse> setTextResponseSale(Sale sale) {
        List<TextResponse> textResponses = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("Phòng đang được giảm giá ").append(sale.getSalePercent()).append("%.\n");
        sb.append("Thời gian kết thúc giảm giá: ").append(formatter.format(sale.getDayEnd())).append(".");
        textResponses.add(new TextResponse(sb.toString()));
        return textResponses;
    }

    private GalleriesResponse.Attachment.Payload.Element setElementService(Service service) {
        GalleriesResponse.Attachment.Payload.Element element = new GalleriesResponse.Attachment.Payload.Element();
        element.setTitle(service.getTitle());
        element.setImage_url(service.getThumbnail());
        element.setSubtitle(currencyVN.format(service.getPrice()) + "/người");
        GalleriesResponse.Attachment.Payload.Element.Button button =
                new GalleriesResponse.Attachment.Payload.Element.Button();
        button.setTitle("Xem chi tiết");
        button.setType("json_plugin_url");
        button.setUrl(serverBaseUrl + "/chatbot/service/" + service.getId());
        element.setButtons(List.of(button));
        return element;
    }

    private List<TextResponse> setTextResponseService(Service service) {
        List<TextResponse> textResponses = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("\uD83C\uDF8A ").append(service.getTitle()).append("\n");
        sb.append("\uD83D\uDCA5 Giá ").append(currencyVN.format(service.getPrice())).append("/người\n");
        sb.append("\uD83C\uDF24 ").append(service.getDescription()).append("\n");
        textResponses.add(new TextResponse(sb.toString()));
        return textResponses;
    }

    private GalleriesResponse.Attachment.Payload.Element setElementProduct(Product product) {
        GalleriesResponse.Attachment.Payload.Element element = new GalleriesResponse.Attachment.Payload.Element();
        element.setTitle(product.getName());
        element.setImage_url(product.getThumbnail());
        element.setSubtitle(product.getDescription());
        GalleriesResponse.Attachment.Payload.Element.Button button =
                new GalleriesResponse.Attachment.Payload.Element.Button();
        button.setTitle("Xem chi tiết");
        button.setType("json_plugin_url");
        button.setUrl(serverBaseUrl + "/chatbot/product/" + product.getId());
        element.setButtons(List.of(button));
        return element;
    }

    private List<TextResponse> setTextResponseProduct(Product product) {
        List<TextResponse> textResponses = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(product.getName()).append("\n");
        if (ObjectUtils.isNotEmpty(product.getDescription())) {
            sb.append(product.getDescription());
        }
        textResponses.add(new TextResponse(sb.toString()));
        return textResponses;
    }

}
