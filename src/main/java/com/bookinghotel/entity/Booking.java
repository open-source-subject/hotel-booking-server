package com.bookinghotel.entity;

import com.bookinghotel.constant.BookingStatus;
import com.bookinghotel.entity.common.UserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends UserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime expectedCheckIn;

  @Column(nullable = false)
  private LocalDateTime expectedCheckOut;

  private LocalDateTime checkIn;

  private LocalDateTime checkOut;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  @Nationalized
  private String note;

  //Link to table Role
  @ManyToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_BOOKING_USER"))
  private User user;

  //Link to table BookingRoomDetail
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
  @JsonIgnore
  private Set<BookingRoomDetail> bookingRoomDetails = new HashSet<>();

  //Link to table BookingServiceDetail
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
  @JsonIgnore
  private Set<BookingServiceDetail> bookingServiceDetails = new HashSet<>();

}
