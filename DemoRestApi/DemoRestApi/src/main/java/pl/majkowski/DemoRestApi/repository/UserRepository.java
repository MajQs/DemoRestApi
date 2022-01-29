package pl.majkowski.DemoRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.majkowski.DemoRestApi.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
