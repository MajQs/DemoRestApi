package pl.majkowski.DemoRestApi.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class UserCSVFileContentException extends RuntimeException{
    private List<String> exceptionList;

    public UserCSVFileContentException(String message, List<String> exceptionList) {
        super(message);
        this.exceptionList = exceptionList;
    }
}
