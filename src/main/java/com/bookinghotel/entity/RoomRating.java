package com.bookinghotel.entity;

import com.bookinghotel.entity.common.FlagUserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room_ratings")
public class RoomRating extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer star;

  @Nationalized
  @Lob
  private String comment;

  //Link to table Room
  @ManyToOne
  @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "FK_ROOM_RATING_ROOM"))
  private Room room;

  //Link to table User
  @ManyToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ROOM_RATING_USER"))
  private User user;

}
