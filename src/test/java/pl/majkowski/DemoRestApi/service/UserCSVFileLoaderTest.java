package pl.majkowski.DemoRestApi.service;

import org.junit.jupiter.api.Test;
import pl.majkowski.DemoRestApi.dao.CSVUser;
import pl.majkowski.DemoRestApi.exception.UserCSVFileNotFoundException;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserCSVFileLoaderTest {
    String path = "src/main/resources/examplefilewithexceptions.csv";

    @Test
    void loadingDefaultPathShouldReturnFiveElementsAsListOfCSVUser() {
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);
        assertEquals(6,userCSVFileLoader.getCSVUsers().size());
    }

    @Test
    void lastUserShouldBeElzbietaZolw() {
        CSVUser lastUser = new CSVUser("Elżbieta","Żółw", Date.valueOf("1988-03-03"),670540120);
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);
        List<CSVUser> userList = userCSVFileLoader.getCSVUsers();
        assertEquals(lastUser,userList.get(5));
    }


    @Test
    void loadingDefaultPathShouldThrowSixExceptions() {
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);
        assertEquals(6,userCSVFileLoader.getExceptions().size());
    }

    @Test
    void loadingWrongPathShouldThrowException() {
        assertThrows(UserCSVFileNotFoundException.class,()->{
            new UserCSVFileLoader("not/existing/path.csv");
        });
    }

    @Test
    void loadingDefaultWrongFileShouldThrowOpenCSVException() {
        assertThrows(IllegalArgumentException.class,()->{
            new UserCSVFileLoader("src/main/resources/examplefilewitherror.csv");
        });
    }
}