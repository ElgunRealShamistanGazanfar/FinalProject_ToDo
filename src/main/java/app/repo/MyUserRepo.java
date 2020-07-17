package app.repo;

import app.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByUsername(String username);
    Optional<MyUser> getAllByFullNameAndEmail(String fullname, String email);
}
