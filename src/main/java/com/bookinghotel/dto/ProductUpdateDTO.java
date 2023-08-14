package com.bookinghotel.dto;

import com.bookinghotel.annotation.ValidFile;
import com.bookinghotel.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateDTO {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    private String thumbnail;

    @ValidFile
    private MultipartFile thumbnailFile;

    private String description;

    private Long serviceId;

}
