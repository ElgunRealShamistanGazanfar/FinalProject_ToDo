package app.repo;

import app.entity.MyGroup;
import app.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MyUserRepo extends JpaRepository<MyUser, Integer> {

    Optional<MyUser> findByUsername(String username);

    Optional<MyUser> deleteAllByGroups(MyGroup mygroup);
    Optional<MyUser> getAllByFullNameAndEmail(String fullname, String email);
    List<MyUser> findAllByGroups(MyGroup myGroup);


}
