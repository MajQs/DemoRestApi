package pl.majkowski.DemoRestApi.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    //private static final Logger logger = LogManager.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(UserCSVFileNotFoundException.class)
    public ResponseEntity<ApiException> userCSVFileNotFoundException(UserCSVFileNotFoundException e){
        HttpStatus httpstatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ApiException.<String>builder()
                .httpStatus(httpstatus)
                .message(e.getMessage())
                .build(),httpstatus);
    }

    @ExceptionHandler(UserCSVFileContentException.class)
    public ResponseEntity<ApiException<List<String>>> userCSVFileContentException(UserCSVFileContentException e){
        HttpStatus httpstatus = HttpStatus.CREATED;
        return new ResponseEntity<>(ApiException.<List<String>>builder()
                .httpStatus(httpstatus)
                .message(e.getMessage())
                .additionalContent(e.getExceptionList())
                .build(),httpstatus);
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<ApiException> userBadRequestException(UserBadRequestException e){
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApiException.<String>builder()
                .httpStatus(httpstatus)
                .message(e.getMessage())
                .build(),httpstatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiException> userNotFoundException(UserNotFoundException e){
        HttpStatus httpstatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ApiException.<String>builder()
                .httpStatus(httpstatus)
                .message(e.getMessage())
                .build(),httpstatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiException<String>> userNotFoundException(HttpMessageNotReadableException e){
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApiException.<String>builder()
                .httpStatus(httpstatus)
                .message("Wrong data. Please verify your request")
                .additionalContent(e.getMessage())
                .build(),httpstatus);
    }
}
