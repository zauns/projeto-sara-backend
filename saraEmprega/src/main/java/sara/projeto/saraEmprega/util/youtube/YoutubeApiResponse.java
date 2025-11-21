package sara.projeto.saraEmprega.util.youtube;

import java.util.List;

public class YoutubeApiResponse {
    public List<Item> items;

    public static class Item {
        public ContentDetails contentDetails;
    }

    public static class ContentDetails {
        public String duration; // formato ISO8601, ex: "PT1H23M45S"
    }
}
