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
        User u1 = new User("Tadeusz","Ziółko",Date.valueOf("1973-03-15"),null);
        User u2 = new User("Ewa","Kowalska",Date.valueOf("1966-06-26"),555777666);
        User u3 = new User("Paulina","Nowak",Date.valueOf("1968-11-03"),222333444);
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

    @Test
    void getOldestUserWithPhoneNoShouldReturnUserEwaKowalska(){
        User user = userRepository.getOldestUserWithPhoneNo();
        assertEquals("Ewa",user.getFirstName());
        assertEquals("Kowalska",user.getLastName());
    }


    @Test
    void getOldestUserWithPhoneNoShouldReturnUserAlaKot(){
        User user = new User("Ala","Kot",Date.valueOf("1962-06-26"),222888555);
        userRepository.save(user);
        User resultUser = userRepository.getOldestUserWithPhoneNo();
        assertEquals(user.getFirstName(),resultUser.getFirstName());
        assertEquals(user.getLastName(),resultUser.getLastName());
        assertEquals(user.getBirthDate(),resultUser.getBirthDate());
        assertEquals(user.getPhoneNo(),resultUser.getPhoneNo());
    }
}