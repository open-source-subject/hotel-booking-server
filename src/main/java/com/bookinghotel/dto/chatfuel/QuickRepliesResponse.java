package com.bookinghotel.dto.chatfuel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuickRepliesResponse {

  private String text;

  @Getter
  @Setter
  public static class QuickReplies {
    private String title;
    private List<String> block_names;
    private String url;
    private String type;
  }

}
