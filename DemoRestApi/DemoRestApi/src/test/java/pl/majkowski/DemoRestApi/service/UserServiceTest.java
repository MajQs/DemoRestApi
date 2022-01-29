package pl.majkowski.DemoRestApi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserCSVFileContentException;
import pl.majkowski.DemoRestApi.exception.UserCSVFileNotFoundException;
import pl.majkowski.DemoRestApi.repository.UserRepository;


import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    String path = "src/main/resources/examplefilewithexceptions.csv";
    String pathError = "src/main/resources/examplefilewitherror.csv";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void loadingDefaultPathShouldThrowContentException(){
        assertThrows(UserCSVFileContentException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase(path);
        });
    }
    @Test
    public void loadingNotExistiongPathShouldThrowNotFoundException(){
        assertThrows(UserCSVFileNotFoundException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase("path/not/exist.csv");
        });
    }
    @Test
    public void loadingWrongFileShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase(pathError);
        });
    }
}