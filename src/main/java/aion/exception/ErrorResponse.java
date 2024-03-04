package aion.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final Throwable cause;
}
