package pl.majkowski.DemoRestApi.exception;

public class UserCSVFileNotFoundException extends RuntimeException{
    public UserCSVFileNotFoundException(String msg){
        super(msg);
    }
}
