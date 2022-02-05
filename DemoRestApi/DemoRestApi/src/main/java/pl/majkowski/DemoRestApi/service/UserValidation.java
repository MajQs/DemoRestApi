package pl.majkowski.DemoRestApi.service;

import java.sql.Date;
import java.util.regex.Pattern;

public class UserValidation {

    private static String generateName(String name){
        name = name.replaceAll(" ","").toLowerCase();
        if(name.isEmpty() ){
            throw new IllegalArgumentException("Name parameter can't be empty");
        }
        Pattern pattern = Pattern.compile("[^a-zżźćńółęąś]", Pattern.CASE_INSENSITIVE);
        if(pattern.matcher(name).find()){
            throw new IllegalArgumentException("Name contains illegal letter. Entered value = " + name);
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    public static String getFirstName(String firstName){
        if(firstName == null ){
            throw new IllegalArgumentException("firstName can't be null");
        }
        return generateName(firstName);
    }

    public static String getLastName(String lastName){
        if(lastName == null ){
            throw new IllegalArgumentException("LastName can't be null");
        }
        return generateName(lastName);
    }

    public static Date getBirthDate(Date birthDate){
        if(birthDate == null ){
            throw new IllegalArgumentException("birthDate can't be null");
        }
        if(birthDate.after(new Date(System.currentTimeMillis()))){
            throw new IllegalArgumentException("Date cant be from the future: entered date = " + birthDate + ", current date = " + new Date(System.currentTimeMillis()));
        }
        return birthDate;
    }

    public static Integer getPhoneNo(Integer phoneNo){
        if(phoneNo == null || (phoneNo >= 100000000 && phoneNo <=999999999)){
            return phoneNo;
        }else{
            throw new IllegalArgumentException("The phone number should consist of 9 digits");
        }
    }


}
