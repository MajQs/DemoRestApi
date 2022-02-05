package pl.majkowski.DemoRestApi.exception;

public class UserBadRequestException extends  RuntimeException{

    public UserBadRequestException(String message) {
        super(message);
    }
}
