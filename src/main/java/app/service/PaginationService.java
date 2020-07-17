package app.service;


import app.entity.MyUser;
import app.entity.Task;
import app.exception.TaskNotFoundEx;
import app.repo.TaskRepo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PaginationService {

    @Autowired
    private final TaskRepo taskRepo;

    private final RegisterService registerService;

    public PaginationService(TaskRepo taskRepo, RegisterService registerService) {
        this.taskRepo = taskRepo;
        this.registerService = registerService;
    }

    public Collection<Task> fetchAll(int loggedUser_id) {
        return taskRepo.findAll().stream().filter(t->t.getMyUser().getId()==loggedUser_id).collect(Collectors.toList());
    }

    public Optional<Task> findTaskById(int id) {

        return taskRepo.findById(id);
    }

    public void addTask(Task task) {
        taskRepo.save(task);
    }

    public Boolean isDone(int task_id) {
        Optional<Task> byId = this.findTaskById(task_id);
        return byId.orElse(new Task(false)).getComplement_status();

    }

    public static Boolean isOverdue(LocalDate curr, Date deadline) {
        Date curr_date = java.sql.Date.valueOf(curr);
        return curr_date.compareTo(deadline) > 0;
    }

    public List<Task> done(int userId) {
        return fetchAll(userId).stream().filter(e -> isDone(e.getId())).collect(Collectors.toList());
    }

    public List<Task> overdue(int userId) {
        return fetchAll(userId).stream().filter(e -> isOverdue(LocalDate.now(), e.getDeadline())).collect(Collectors.toList());

    }

    public List<Task> today(int userId) {
        return fetchAll(userId).stream().filter(e -> e.getDeadline().equals(java.sql.Date.valueOf(LocalDate.now()))).collect(Collectors.toList());
    }

    public void deleteTask(int id) {
        taskRepo.deleteById(id);
    }

    public void addToimportant(int id) {
        Task task = findTaskById(id).orElse(new Task(false));
        task.setStatus("important");
        taskRepo.deleteById(id);
        taskRepo.save(task);


    }

    public List<Task> important(int userId) {
        List<Task> imp = fetchAll(userId).stream().filter(t -> t.getStatus().equals("important")).collect(Collectors.toList());
        if (imp.isEmpty()){
            imp.add(0,new Task(false));
        }
        return imp;
    }


    public Page<Task> pageForToday(Pageable pageable){
        List<Task> pg = fetchAll((int)registerService
                .logged_user().get().getId())
                .stream().filter(t->t.getDeadline()
                        .equals(java.sql.Date.valueOf(LocalDate.now())))
                .collect(Collectors.toList());
        Page<Task> res =new PageImpl<Task>(pg,pageable, pg.size());
        return res;
    }





}
