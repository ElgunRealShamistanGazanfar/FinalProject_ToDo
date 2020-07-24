package app.controller;

import app.entity.Task;
import app.service.PaginationService;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Log4j2
@Controller
@RequestMapping("/")
public class ReportController {

    @Autowired
    RegisterService registerService;

    @Autowired
    PaginationService paginationService;

    @Autowired
    TaskService taskService;
//
//
//    @GetMapping("/report/table/paged")
//    public String getPaged(Model model, Pageable pageable) {
//      Page<Task> pageForToday = paginationService.pageForToday(pageable);
//       model.addAttribute("tasks",pageForToday);
//        return "tasks-dashboard";
//    }

        @GetMapping("tasks-dashboard")
    public String handle_get1(
            Model model,
            Pageable pageable
    ) {
        int LoggedUserId=(int) registerService.logged_user().get().getId();
        Page<Task> tasks=paginationService.fetchAll(pageable);

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
    public String handle_post1(@RequestParam("task-type") String tsk_status, Model model, Pageable pageable) {
         int LoggedUserId=(int) registerService.logged_user().get().getId();
        log.info("POST -> /tasks-dashboards ->" + tsk_status);
        String username = registerService.logged_user().get().getFullName();

        // it will be shown in description part
        List<Task> toBeShown = new ArrayList<>();
        toBeShown.add(taskService.important(LoggedUserId).get(0));
        model.addAttribute("shown", toBeShown);


        registerService.addProfile(model);

          switch (tsk_status){
            case "overdue":
                model.addAttribute("tasks",paginationService.pageForOverdue(pageable));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "today":
                model.addAttribute("tasks",paginationService.pageForToday(pageable));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "done":
                model.addAttribute("tasks",paginationService.pageForDone(pageable));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;
            case "available":
                model.addAttribute("tasks",paginationService.fetchAll(pageable));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                break;

             case "important":
                 model.addAttribute("tasks",paginationService.pageForImportant(pageable));
                 model.addAttribute("username", username);
                 registerService.addProfile(model);
                  break;
        }


        return "tasks-dashboard";
    }



}
