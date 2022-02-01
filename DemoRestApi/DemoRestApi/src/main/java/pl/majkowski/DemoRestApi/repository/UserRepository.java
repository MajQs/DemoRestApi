package pl.majkowski.DemoRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.majkowski.DemoRestApi.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNo(Integer phoneNo);

    @Query(value = "SELECT count(*) FROM User",
            nativeQuery = true)
    int getUsersCount();

    @Query(value = "SELECT * " +
            "FROM User " +
            "WHERE phoneno is not null " +
            "ORDER BY birthdate " +
            "LIMIT 1",
            nativeQuery = true)
    User getOldestUserWithPhoneNo();

}



