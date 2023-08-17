package com.bookinghotel.dto.chatfuel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendImageResponse {

  private Attachment attachment;

  @Getter
  @Setter
  public static class Attachment {
    private String type = "image";
    private Payload payload;

    @Getter
    @Setter
    public static class Payload {
      private String url;
    }
  }

}
