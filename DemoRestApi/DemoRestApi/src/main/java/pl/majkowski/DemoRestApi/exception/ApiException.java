package pl.majkowski.DemoRestApi.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ApiException<T>  {
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp = ZonedDateTime.now();
    private final String message;
    private T additionalContent;
/*
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
    }*/

}
