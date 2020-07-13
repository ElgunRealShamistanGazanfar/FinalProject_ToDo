package app.controller;


import app.entity.Task;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
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
import java.util.stream.Collectors;

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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("username", username);
        model.addAttribute("tasks", tasks);
        List<Task> toBeShown = new ArrayList<>();
        toBeShown.add(taskService.important().get(0));
        model.addAttribute("shown", toBeShown);
        log.info("GET -> /tasks-dashboards");
        return "tasks-dashboard";
    }

    @PostMapping("tasks-dashboard")
    public String handle_post1( @RequestParam("task-type") String tsk_status, Model model) {
        log.info("POST -> /tasks-dashboards ->" + tsk_status);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

          switch (tsk_status){
            case "overdue":
                model.addAttribute("tasks",taskService.overdue());
                model.addAttribute("username", username);
                break;
            case "today":
                model.addAttribute("tasks",taskService.today());
                model.addAttribute("username", username);
                break;
            case "done":
                model.addAttribute("tasks",taskService.done());
                model.addAttribute("username", username);
                break;
            case "available":
                model.addAttribute("tasks",taskService.fetchAll());
                model.addAttribute("username", username);
                break;

             case "important":
                 model.addAttribute("tasks",taskService.important());
                 model.addAttribute("username", username);
                  break;
        }


        return "tasks-dashboard";
    }




    @GetMapping("tasks-archive")
    public String handle_tasks_archive_get() {
        log.info("GET -> /tasks-archive");
        return "tasks-archive";
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
        Task task = new Task(title,deadline,imageFile.getBytes(),curr,"available",content, false);
        taskService.addTask(task);
        log.info(pf("POST -> /task-add: %s image: %s ", task, imageFile.getResource()));
        return new RedirectView("tasks-dashboard");
    }




}
