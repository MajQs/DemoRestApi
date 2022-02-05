package pl.majkowski.DemoRestApi.service;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    @Test
    void correctFirstNameShouldReturnCorrectedString(){
        assertEquals("Correctfirstname", UserValidation.getFirstName(" correct firstName     "));
    }
    @Test
    void incorrectFirstNameShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getFirstName(null);});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getFirstName("   ");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getFirstName("incorrect9input");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getFirstName("\tincorrect input");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getFirstName("incorrect^(#$input");});
    }

    @Test
    void correctLastNameShouldReturnCorrectedString(){
        assertEquals("Correctlastname", UserValidation.getLastName(" correct lastName     "));
    }
    @Test
    void incorrectLastNameShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getLastName(null);});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getLastName("   ");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getLastName("incorrect9input");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getLastName("\tincorrect input");});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getLastName("incorrect^(#$input");});
    }

    @Test
    void correctPhoneNoShouldReturnPhoneNO(){
        assertEquals(888777666, UserValidation.getPhoneNo(888777666));
        assertEquals(100000000, UserValidation.getPhoneNo(100000000));
        assertEquals(999999999, UserValidation.getPhoneNo(999999999));
    }

    @Test
    void incorrectPhoneNoShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getPhoneNo(000000000);});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getPhoneNo(90000000);});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getPhoneNo(1000000000);});
    }

    @Test
    void correctDateShouldReturnDate(){
        assertEquals(Date.valueOf("2014-02-14"), UserValidation.getBirthDate(Date.valueOf("2014-02-14")));
        assertEquals(new Date(System.currentTimeMillis()), UserValidation.getBirthDate(new Date(System.currentTimeMillis())));
    }

    @Test
    void incorrectDateShouldThrowIllegalArgumentException(){
        Date date = new Date(System.currentTimeMillis()+1000000);
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getBirthDate(null);});
        assertThrows(IllegalArgumentException.class,()-> {UserValidation.getBirthDate(date);});
    }

}