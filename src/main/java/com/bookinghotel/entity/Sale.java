package com.bookinghotel.entity;

import com.bookinghotel.entity.common.FlagUserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sales")
public class Sale extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime dayStart;

  @Column(nullable = false)
  private LocalDateTime dayEnd;

  @Column(nullable = false)
  private Integer salePercent;

  //Link to table Room
  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "sale")
  @Where(clause = "delete_flag = 0")
  @JsonIgnore
  private Set<Room> rooms = new HashSet<>();

  @PreRemove
  private void removeSaleFromRooms() {
    for (Room room : rooms) {
      room.setSale(null);
    }
  }

}
