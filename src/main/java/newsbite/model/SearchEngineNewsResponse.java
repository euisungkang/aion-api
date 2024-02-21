package newsbite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class SearchEngineNewsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, dd MMM yyyy HH:mm:ss Z")
    private ZonedDateTime lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;


    @Data
    public static class Item {
        private String title;
        private String originallink;
        private String link;
        private String description;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, dd MMM yyyy HH:mm:ss Z")
        private ZonedDateTime pubDate;
    }
}
