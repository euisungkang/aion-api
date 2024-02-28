package newsbite.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewsQueryResponse {
    private SearchEngineNewsResponse source;
    private String res;
}
