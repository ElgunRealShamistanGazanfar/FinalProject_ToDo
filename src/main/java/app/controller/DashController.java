package app.controller;


import app.entity.Task;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/delete")
public class DashController {

    private final TaskService taskService;

    public DashController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public RedirectView delete_task(@PathVariable int id){
        taskService.deleteTask(id);
        return new RedirectView("/tasks-dashboard");

    }
}
