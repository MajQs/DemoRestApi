package pl.majkowski.DemoRestApi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pl.majkowski.DemoRestApi.dto.UserAgeDTO;
import pl.majkowski.DemoRestApi.dto.UserDto;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserCSVFileContentException;
import pl.majkowski.DemoRestApi.exception.UserCSVFileNotFoundException;
import pl.majkowski.DemoRestApi.exception.UserNotFoundException;
import pl.majkowski.DemoRestApi.repository.UserRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    String path = "src/main/resources/examplefile.csv";
    String pathExceptions = "src/main/resources/examplefilewithexceptions.csv";
    String pathError = "src/main/resources/examplefilewitherror.csv";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnThreeUsers(){
        List<User> userList = List.of(
                new User(1L,"Stefan","Testowy", Date.valueOf("1988-11-11"),600700800),
                new User(2L, "Maria","Ziółko", Date.valueOf("1999-1-1"),555666777),
                new User(3L,"Marian","Kowalewski", Date.valueOf("1950-10-01"),670540120)
        );

        Page<User> page = new PageImpl<User>(userList);
        when(userRepository.findAll(PageRequest.of(1,3))).thenReturn(page);

        assertEquals(3, userService.getAllUsers(1,3).size());
    }


    @Test
    void loadingDefaultPathShouldSaveFiveUsersToDatabase(){
        userService.loadUsersFromCSVFileToDatabase(path);
        verify(userRepository, times(5)).save(any(User.class));
    }

    @Test
    void loadingExceptionPathShouldSaveFiveUsersToDatabase(){
        assertThrows(UserCSVFileContentException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase(pathExceptions);
        });
        verify(userRepository, times(5)).save(any(User.class));
    }
    @Test

    void loadingDefaultPathShouldReturnFiveAsCount(){
        when(userRepository.getUsersCount()).thenReturn(5);
        assertEquals(5,userService.getUsersCount());
    }

    @Test
    void loadingNotExistingPathShouldReturnNotFoundException(){
        assertThrows(UserCSVFileNotFoundException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase("not/exist/path.csv");
        });
    }

    @Test
    void loadingPathWithBadFileShouldReturnIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase(pathError);
        });
    }

    @Test
    void loadingDefaultPathShouldReturnContentException(){
        when(userRepository.existsByPhoneNo(anyInt())).thenReturn(true);
        assertThrows(UserCSVFileContentException.class,() -> {
            userService.loadUsersFromCSVFileToDatabase(path);
        });
    }

    @Test
    void getOldestUserWithPhoneShouldReturnAgeTwenty(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20);

        when(userRepository.getOldestUserWithPhoneNo()).thenReturn(new User("Ala","Kot",new Date(cal.getTimeInMillis()),222888555));
        assertEquals(20,userService.getOldestUserWithPhone().getAge());
    }
    @Test
    void getOldestUserWithPhoneShouldReturnAgeNineteen(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20);
        cal.add(Calendar.MONTH, 1);

        when(userRepository.getOldestUserWithPhoneNo()).thenReturn(new User("Ala","Kot",new Date(cal.getTimeInMillis()),222888555));
        assertEquals(19,userService.getOldestUserWithPhone().getAge());
    }

    @Test
    void deletingUserIDOneShouldReturnOK(){
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    void deletingUserIdOneShouldInvokeDeleteByIdOne(){
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    void deletingNotExistingUserIdShouldThrowNotFoundException(){
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void deletingAllShouldInvokeDeleteAll(){
        userService.deleteAll();
        verify(userRepository,times(1)).deleteAll();
    }

    @Test
    void getUserByIdShouldReturnUserDTO(){
        User user = new User(4L, "name", "lastname", Date.valueOf("2021-12-12"), null);

        when(userRepository.existsById(4L)).thenReturn(true);
        when(userRepository.findById(4L)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(4L);
        assertEquals(user.getFirstName(),result.getName());
        assertEquals(user.getLastName(),result.getSurename());
        assertEquals(user.getBirthDate(),result.getBirth_day());
        assertEquals(user.getPhoneNo(),result.getPhone_number());
    }

    @Test
    void getUserByIdShouldReturnNotFoundException(){
        assertThrows(UserNotFoundException.class, ()->{
            userService.getUserById(4L);
        });
    }
}