package com.bookinghotel.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataMailDTO {

    private String to;

    private String subject;

    private String content;

    private Map<String, Object> properties;

}