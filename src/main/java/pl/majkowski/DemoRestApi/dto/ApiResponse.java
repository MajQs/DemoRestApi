package pl.majkowski.DemoRestApi.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final T message;
    private final HttpStatus httpStatus;
    private final LocalDateTime createDateTime;

    public ApiResponse(T message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.createDateTime = LocalDateTime.now();
    }

}
