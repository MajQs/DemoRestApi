package pl.majkowski.DemoRestApi.dao;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CSVUser {

    @CsvBindByName(column = "first_name", required = true)
    private String firstName;

    @CsvBindByName(column = "last_name", required = true)
    private String lastName;

    @CsvBindByName(column = "birth_date", required = true)
    @CsvDate(value = "yyyy.MM.dd")
    private Date birthDate;

    @CsvBindByName(column = "phone_no")
    private Integer phoneNo;
}
