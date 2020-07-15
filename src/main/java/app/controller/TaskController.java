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



    /**
     * http://localhost:8080/tasks-dashboard
     */
    @GetMapping("tasks-dashboard")
    public String handle_get1(
            Model model
    ) {
        int LoggedUserId=(int) registerService.logged_user().get().getId();
        Collection<Task> tasks=taskService.fetchAll(LoggedUserId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = registerService.logged_user().get().getFullName();
        model.addAttribute("username", username);
        model.addAttribute("tasks", tasks);
        List<Task> toBeShown = new ArrayList<>();
        toBeShown.add(taskService.important(LoggedUserId).get(0));
        model.addAttribute("shown", toBeShown);
        registerService.addProfile(model);
        log.info("GET -> /tasks-dashboards");
        return "tasks-dashboard";
    }

    @PostMapping("tasks-dashboard")
    public String handle_post1( @RequestParam("task-type") String tsk_status, Model model) {
         int LoggedUserId=(int) registerService.logged_user().get().getId();
        log.info("POST -> /tasks-dashboards ->" + tsk_status);
        String username = registerService.logged_user().get().getFullName();

        List<Task> toBeShown = new ArrayList<>();
        toBeShown.add(taskService.important(LoggedUserId).get(0));
        model.addAttribute("shown", toBeShown);
        registerService.addProfile(model);

          switch (tsk_status){
            case "overdue":
                model.addAttribute("tasks",taskService.overdue(LoggedUserId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "today":
                model.addAttribute("tasks",taskService.today(LoggedUserId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "done":
                model.addAttribute("tasks",taskService.done(LoggedUserId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "available":
                model.addAttribute("tasks",taskService.fetchAll(LoggedUserId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;

             case "important":
                 model.addAttribute("tasks",taskService.important(LoggedUserId));
                 model.addAttribute("username", username);
                 registerService.addProfile(model);
                  break;
        }


        return "tasks-dashboard";
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
