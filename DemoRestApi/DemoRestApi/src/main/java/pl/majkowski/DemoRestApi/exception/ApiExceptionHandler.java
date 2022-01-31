package pl.majkowski.DemoRestApi.exception;

import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    private ResponseEntity<ApiException> createResponse(HttpStatus httpStatus, String msg){
        return new ResponseEntity(new ApiException(
                msg,
                httpStatus,
                ZonedDateTime.now()),httpStatus);
    }


    @ExceptionHandler(UserCSVFileNotFoundException.class)
    public ResponseEntity<ApiException> userCSVFileNotFoundException(UserCSVFileNotFoundException e){
        return createResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(UserCSVFileContentException.class)
    public ResponseEntity<ApiException> userCSVFileContentException(UserCSVFileContentException e){
        ApiException<List<String>> apiException = new ApiException<List<String>>(
                e.getMessage(),
                HttpStatus.CREATED,
                ZonedDateTime.now(),
                e.getExceptionList()
        );

        return new ResponseEntity(apiException,HttpStatus.CREATED);
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<ApiException> userBadRequestException(UserBadRequestException e){
        return createResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }

}
