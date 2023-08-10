package com.bookinghotel.entity;

import com.bookinghotel.entity.common.UserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking_room_details")
public class BookingRoomDetail extends UserDateAuditing {

  @EmbeddedId
  BookingRoomDetailId id;

  //Link to table Booking
  @ManyToOne
  @MapsId("bookingId")
  @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "FK_BOOKING_ROOM_DETAIL_BOOKING"))
  private Booking booking;

  //Link to table Room
  @ManyToOne
  @MapsId("roomId")
  @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "FK_BOOKING_ROOM_DETAIL_ROOM"))
  private Room room;

  @Column(nullable = false)
  private Long price;

  private Integer salePercent;

  public BookingRoomDetail(Booking booking, Room room) {
    this.id = new BookingRoomDetailId(booking.getId(), room.getId());
    this.booking = booking;
    this.room = room;
  }

}
