package app.service;


import app.entity.Task;
import app.repo.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Collection<Task> fetchAll() {
        return taskRepo.findAll();
    }
    public void addTask(){

    }
}
