package app.controller;


import app.entity.Task;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class DashController {

    private final TaskService taskService;

    public DashController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete_task(@PathVariable int id){
        log.info(String.format("GET -> deleting task with id: %d", id));
        taskService.deleteTask(id);
        return new RedirectView("/tasks-dashboard");

    }

    @GetMapping("/add/{id}")
    public RedirectView add_important(@PathVariable int id, Model model){
        taskService.addToimportant(id);
        log.info(String.format("Element with id %d added to importants", id));
        return new RedirectView("/tasks-dashboard");

    }
}
