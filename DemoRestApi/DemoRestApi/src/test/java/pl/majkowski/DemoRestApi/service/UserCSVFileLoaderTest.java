package pl.majkowski.DemoRestApi.service;

import org.junit.jupiter.api.Test;
import pl.majkowski.DemoRestApi.exception.UserCSVFileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserCSVFileLoaderTest {
    String path = "src/main/resources/examplefilewithexceptions.csv";

    @Test
    void loadingDefaultPathShouldReturnFiveElementsAsListOfCSVUser() {
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);
        assertEquals(5,userCSVFileLoader.getCSVUsers().size());
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