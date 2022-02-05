package pl.majkowski.DemoRestApi.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

public class CalenderFormatter {

    public static Integer getAge(Date date1){
        Calendar a = Calendar.getInstance(Locale.getDefault());
        Calendar b = Calendar.getInstance(Locale.getDefault());
        a.setTime(date1);
        b.setTime(new Date(System.currentTimeMillis()));
        Integer yearDifference = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if(a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))){
            yearDifference--;
        }
        return yearDifference;
    }
}
