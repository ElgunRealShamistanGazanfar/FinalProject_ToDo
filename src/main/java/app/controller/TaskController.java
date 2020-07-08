package app.controller;


import app.entity.Task;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Controller
@RequestMapping("/")
public class TaskController {

    private static String pf(String fmt, Object... a) {
        return String.format(fmt, a);
    }

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * http://localhost:8080/tasks-dashboard
     */
    @GetMapping("tasks-dashboard")
    public String handle_get1(
            Model model
    ) {
        Collection<Task> tasks=taskService.fetchAll();
        model.addAttribute("tasks", tasks);

        log.info("GET -> /tasks-dashboards");
        return "tasks-dashboard";
    }

    @GetMapping("create-task")
    public String handle_goAdd_Task_get() {
        log.info("GET -> /go_add_task");
        return "add-task";
    }




    @PostMapping("create-task")
    public RedirectView handle_addTask_post2(
            @RequestParam("new-task-text")String content
            ,@RequestParam("title")String title
            ,@RequestParam("task-card-date") Date deadline
            ,@RequestParam("myImage") MultipartFile imageFile
    ) throws IOException {
        LocalDate curr = LocalDate.now();
        Task task = new Task(title,deadline,imageFile.getBytes(),curr,content,taskService.checkComplementStatus(curr,deadline));
        taskService.addTask(task);
        log.info(pf("POST -> /task-add: %s image: %s ", task, imageFile.getResource()));
        return new RedirectView("tasks-dashboard");
    }




}
