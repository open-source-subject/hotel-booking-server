package com.bookinghotel.entity;

import com.bookinghotel.entity.common.FlagUserDateAuditing;
import com.bookinghotel.util.BeanUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String thumbnail;

  @Nationalized
  @Lob
  private String description;

  //Link to table Service
  @ManyToOne
  @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_SERVICE"))
  private Service service;

  @PreRemove
  private void destroyThumbnail() {
    if(ObjectUtils.isNotEmpty(thumbnail)) {
      UploadFileUtil uploadFile = BeanUtil.getBean(UploadFileUtil.class);
      uploadFile.destroyFileWithUrl(thumbnail);
    }
  }

}
