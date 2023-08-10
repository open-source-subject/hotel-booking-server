package com.bookinghotel.entity;

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
@Table(name = "posts")
public class Post extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nationalized
  @Column(nullable = false)
  private String title;

  @Nationalized
  @Lob
  @Column(nullable = false)
  private String content;

  //Link to table User
  @ManyToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_POST_USER"))
  private User user;

  //Link to table Media
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
  @Where(clause = "delete_flag = 0")
  @JsonIgnore
  private Set<Media> medias = new HashSet<>();

}
