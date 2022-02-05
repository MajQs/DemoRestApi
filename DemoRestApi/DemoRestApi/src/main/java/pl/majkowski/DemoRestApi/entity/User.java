package pl.majkowski.DemoRestApi.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Column(name = "birthdate")
    private Date birthDate;

    @Column(unique = true,name = "phoneno")
    private Integer phoneNo;

    public User(String firstName, String lastName, Date birthDate, Integer phoneNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNo = phoneNo;
    }
}
