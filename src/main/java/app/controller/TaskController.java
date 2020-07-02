package app.controller;


import app.entity.Task;
import app.service.TaskService;
import app.session.TaskDetails;
import app.session.UserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;

@Log4j2
@Controller
@RequestMapping("/")
@SessionAttributes(
        names = { TaskDetails.ATTR },
        types = { TaskDetails.class }
)
public class TaskController {

    private static String pf(String fmt, Object... a) {
        return String.format(fmt, a);
    }

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // how to create our attribute
    @ModelAttribute(TaskDetails.ATTR)
    public TaskDetails create0000() {
        return new TaskDetails();
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

    @GetMapping("add-task")
    public String handle_get2() {
        log.info("GET -> /add-task");
        return "add-task";
    }

    @PostMapping("add-task")
    public RedirectView handle_booking_post(
            @ModelAttribute(TaskDetails.ATTR) TaskDetails td
    ) {
        log.info(pf("POST -> /booking: %s", td));
        return new RedirectView("tasks-dashboard");
    }



}
