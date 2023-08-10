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
@Table(name = "booking_service_details")
public class BookingServiceDetail extends UserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Integer amount;

  //Link to table Booking
  @ManyToOne
  @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "FK_BOOKING_SERVICE_DETAIL_BOOKING"))
  private Booking booking;

  //Link to table Service
  @ManyToOne
  @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_BOOKING_SERVICE_DETAIL_SERVICE"))
  private Service service;

}
