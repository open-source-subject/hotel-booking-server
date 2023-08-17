package com.bookinghotel.dto.init;

import com.bookinghotel.entity.Media;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomInitJSON {

  private Long id;

  private String name;

  private Long price;

  private String type;

  private String bed;

  private Integer size;

  private Integer capacity;

  private String services;

  private String description;

  private Set<Media> medias = new HashSet<>();

}
