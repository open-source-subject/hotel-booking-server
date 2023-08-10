package com.bookinghotel.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class BookingRoomDetailId implements Serializable {

  @Column(name = "booking_id")
  private Long bookingId;

  @Column(name = "room_id")
  private Long roomId;

}
