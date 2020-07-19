package app.repo;

import app.entity.MyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepo extends JpaRepository<MyGroup, Integer> {

    List<MyGroup> findAllByGroupNameIsLike(String groupname);
    Optional<MyGroup> findById(int id);

    Optional<MyGroup> deleteById(int id);

}
