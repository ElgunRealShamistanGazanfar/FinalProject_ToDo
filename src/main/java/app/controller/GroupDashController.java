package app.controller;

import app.entity.Task;
import app.service.PaginationService;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class GroupDashController {


    @Autowired
    RegisterService registerService;

    @Autowired
    PaginationService paginationService;

    @Autowired
    TaskService taskService;


    @GetMapping("chat")
    public String chat_get(){
        return "chat";

    }
    @GetMapping("chat/{groupId}")
    public String chat_get2(@PathVariable int groupId, Model model, HttpServletResponse response){


        return "chat";

    }

//    @GetMapping("group-dashboard")
//    public String handle_get1(
//            Model model,
//            Pageable pageable
//    ) {
//        int LoggedUserId=(int) registerService.logged_user().get().getId();
//        Page<Task> tasks=paginationService.fetchAll(pageable);
//
//        String username = registerService.logged_user().get().getFullName();
//        model.addAttribute("username", username);
//        model.addAttribute("tasks", tasks);
//        List<Task> toBeShown = new ArrayList<>();
//        toBeShown.add(taskService.important(LoggedUserId).get(0));
//        model.addAttribute("shown", toBeShown);
//        registerService.addProfile(model);
//        log.info("GET -> /tasks-dashboards");
//        return "tasks-dashboard";
//    }

//
//    PostMapping("send")
//        public RedirectView chat_post
}
