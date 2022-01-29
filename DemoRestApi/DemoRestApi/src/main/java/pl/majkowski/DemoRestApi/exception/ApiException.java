package pl.majkowski.DemoRestApi.exception;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GeneratorType;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@ToString
@Getter
public class ApiException<T>  {
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final String message;
    private T additionalContent;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, T additionalContent) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.additionalContent = additionalContent;
    }

}
