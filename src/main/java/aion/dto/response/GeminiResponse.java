package aion.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    private List<Candidate> candidates;

    @Getter
    public static class Candidate {
        private Content content;
    }

    @Getter
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Getter
    public static class Part {
        private String text;
    }
}
