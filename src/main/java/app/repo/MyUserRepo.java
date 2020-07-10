package app.repo;

import app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
