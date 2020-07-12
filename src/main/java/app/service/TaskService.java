package app.service;


import app.entity.Task;
import app.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Collection<Task> fetchAll() {
        return taskRepo.findAll();
    }

    public Optional<Task> findTaskById(int id){

        return taskRepo.findById(id);
    }

    public void addTask(Task task){
        taskRepo.save(task);
    }

    public Boolean isDone(int task_id){
        Optional<Task> byId = this.findTaskById(task_id);
        return byId.orElse(new Task(false)).getComplement_status();

    }

    public static Boolean isOverdue(LocalDate curr,Date deadline){
        Date curr_date  = java.sql.Date.valueOf(curr);
        return curr_date.compareTo(deadline) > 0;
    }

    public List<Task> done(){
        return fetchAll().stream().filter(e -> isDone(e.getId())).collect(Collectors.toList());
    }

    public List<Task> overdue(){
        return fetchAll().stream().filter(e -> isOverdue(LocalDate.now(), e.getDeadline())).collect(Collectors.toList());

    }

    public List<Task> today(){
        return fetchAll().stream().filter(e -> e.getDeadline().equals(java.sql.Date.valueOf(LocalDate.now()))).collect(Collectors.toList());
    }

    public void deleteTask(int id){
        taskRepo.deleteById(id);
    }


}
