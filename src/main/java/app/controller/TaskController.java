package app.controller;


import app.entity.Task;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class TaskController {

    private static String pf(String fmt, Object... a) {
        return String.format(fmt, a);
    }

    private final TaskService taskService;
    private final RegisterService registerService;


    public TaskController(TaskService taskService, RegisterService registerService) {
        this.taskService = taskService;
        this.registerService = registerService;
    }


    @GetMapping("tasks-archive")
    public String handle_tasks_archive_get(Model model) {
        if (registerService.logged_user().get().getProfile()!=null){
            model.addAttribute("profile", registerService.logged_user().get().getProfile());
        }else {
            model.addAttribute("profile", "/img/user-icon-with-background.svg");
        }
        log.info("GET -> /tasks-archive");
        return "tasks-archive";
    }
    @GetMapping("create-task")
    public String handle_goAdd_Task_get(Model model) {
        log.info("GET -> /go_add_task");

        String username = registerService.logged_user().get().getFullName();
        registerService.addProfile(model);
        model.addAttribute("username", username);
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
        Task task = new Task(title,deadline,curr,"available",content, false, registerService.logged_user().get());
        taskService.addImageAndSaveToDB(task,imageFile);
        log.info(pf("POST -> /task-add: %s image: %s ", task, imageFile.getResource()));
        return new RedirectView("tasks-dashboard");
    }




}
