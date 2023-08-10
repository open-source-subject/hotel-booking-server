package com.bookinghotel.entity;

import com.bookinghotel.constant.RoomType;
import com.bookinghotel.entity.common.FlagUserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nationalized
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private String bed;

  @Column(nullable = false)
  private Integer size;

  @Column(nullable = false)
  private Integer capacity;

  @Column(nullable = false)
  private String services;

  @Lob
  @Nationalized
  @Column(nullable = false)
  private String description;

  //Link to table Sale
  @ManyToOne
  @JoinColumn(name = "sale_id", foreignKey = @ForeignKey(name = "FK_ROOM_SALE"))
  private Sale sale;

  //Link to table Media
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  @Where(clause = "delete_flag = 0")
  @JsonIgnore
  private Set<Media> medias = new HashSet<>();

  //Link to table BookingDetail
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  @JsonIgnore
  private Set<BookingRoomDetail> bookingDetails = new HashSet<>();

}
