package com.bookinghotel.dto;

import com.bookinghotel.constant.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MediaDTO {

    private Long id;

    private String url;

    private MediaType type;

}
