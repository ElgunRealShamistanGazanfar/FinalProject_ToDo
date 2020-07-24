package app.repo;

import app.entity.MyUser;
import app.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.Optional;

@Repository
@Transactional
public interface TaskRepo extends JpaRepository<Task, Integer> {

    Page<Task> findAllByMyUser(MyUser myUser, Pageable page);
    Page<Task> findAll(Pageable page);
    Page<Task> findAllByDeadline(Date date, Pageable page);
    Page<Task> findAllByStatus(String status,Pageable page);
    Page<Task> findAllByDeadlineAfter(Date date, Pageable page);
    Page<Task> findAllByComplete(boolean bool,Pageable page);


            

}
