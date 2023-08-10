package com.bookinghotel.entity;

import com.bookinghotel.constant.MediaType;
import com.bookinghotel.entity.common.FlagUserDateAuditing;
import com.bookinghotel.util.BeanUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medias")
public class Media extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String url;

  @Enumerated(EnumType.STRING)
  private MediaType type;

  //Link to table Post
  @ManyToOne
  @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_MEDIA_POST"))
  private Post post;

  //Link to table Room
  @ManyToOne
  @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "FK_MEDIA_ROOM"))
  private Room room;

  @PreRemove
  private void destroyUrlMedia() {
    if(ObjectUtils.isNotEmpty(url)) {
      UploadFileUtil uploadFile = BeanUtil.getBean(UploadFileUtil.class);
      uploadFile.destroyFileWithUrl(url);
    }
  }

}
