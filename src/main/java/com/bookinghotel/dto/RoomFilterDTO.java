package com.bookinghotel.dto;

import com.bookinghotel.constant.RoomType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomFilterDTO {

    @Parameter(description = "fromDate format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkin = LocalDate.now();

    @Parameter(description = "toDate format yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkout = LocalDate.now().plusDays(1);

    private RoomType roomType;

    private Integer capacity;

}
