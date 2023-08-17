package com.bookinghotel.dto.chatfuel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GalleriesResponse {

  private Attachment attachment;

  @Getter
  @Setter
  public static class Attachment {
    private String type = "template";
    private Payload payload;

    @Getter
    @Setter
    public static class Payload {
      private String template_type = "generic";
      private String image_aspect_ratio = "horizontal";
      private List<Element> elements;

      @Getter
      @Setter
      public static class Element {
        private String title;
        private String image_url;
        private String subtitle;
        private List<Button> buttons;

        @Getter
        @Setter
        public static class Button {
          private String title;
          private String url;
          private String type;
        }
      }
    }
  }

}
