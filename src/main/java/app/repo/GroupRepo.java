package app.repo;

import app.entity.MyGroup;
import app.entity.MyMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface GroupRepo extends JpaRepository<MyGroup, Integer> {

    List<MyGroup> findAllByGroupNameIsLike(String groupname);

    Optional<MyGroup> findById(int id);


    Optional<MyGroup> deleteById(int id);

}
