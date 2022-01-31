package pl.majkowski.DemoRestApi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.majkowski.DemoRestApi.entity.User;
import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User u1 = new User("Tadeusz","Ziółko",new Date(System.currentTimeMillis()),null);
        User u2 = new User("Ewa","Kowalska",new Date(System.currentTimeMillis()),555777666);
        User u3 = new User("Paulina","Nowak",new Date(System.currentTimeMillis()),222333444);
        List<User> userList = List.of(u1,u2,u3);
        userRepository.saveAll(userList);
    }

    @Test
    void itShouldReturnFourAsUsersCount(){
        User u = new User("Michał","Pazura",new Date(System.currentTimeMillis()),null);
        userRepository.save(u);
        assertEquals(4,userRepository.getUsersCount());
    }

    @Test
    void itShouldReturnThreeAsUsersCount(){
        assertEquals(3,userRepository.getUsersCount());
    }

    @Test
    void itShouldReturnTrueIfPhoneNoExist(){
        assertTrue(userRepository.existsByPhoneNo(222333444));
    }

    @Test
    void itShouldReturnFalseIfPhoneNoNotExist(){
        assertFalse(userRepository.existsByPhoneNo(111111111));
        assertFalse(userRepository.existsByPhoneNo(111111112));
    }

}