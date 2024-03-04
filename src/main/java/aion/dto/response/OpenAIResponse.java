package aion.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {
    private List<Choice> choices;
    private long created;
    private String id;
    private String model;
    private String object;
    private Usage usage;

    @Getter
    public static class Choice {
        private String finish_reason;
        private int index;
        private Message message;
        private Object logprobs;
    }

    @Getter
    public static class Message {
        private String content;
        private String role;
    }

    @Getter
    public static class Usage {
        private int completion_tokens;
        private int prompt_tokens;
        private int total_tokens;
    }
}
