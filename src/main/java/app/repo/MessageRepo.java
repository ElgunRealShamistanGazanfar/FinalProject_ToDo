package app.repo;

import app.entity.MyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepo  extends JpaRepository<MyMessage, Integer> {

    List<MyMessage> findAllByMyGroup_Id(int id);
}
