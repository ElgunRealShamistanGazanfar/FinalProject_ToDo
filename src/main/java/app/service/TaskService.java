package app.service;


import app.entity.Task;
import app.repo.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Collection<Task> fetchAll() {
        return taskRepo.findAll();
    }

    public void addTask(Task task){
        taskRepo.save(task);
    }

    public static String checkComplementStatus (LocalDate curr, Date deadline){
        LocalDate localDeadline = LocalDate.of(deadline.getYear()+2, deadline.getMonth()+1, deadline.getDay()+3);
    int res = curr.compareTo(localDeadline);
    if (res>0){
        return "complete";
    }
        return "not complete";

    }
}
