package app.controller;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import app.service.GroupDashService;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class GroupDashController {


    @Autowired
    RegisterService registerService;

    @Autowired
    GroupDashService groupDashService;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    MyUserRepo myUserRepo;

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


    @PostMapping("group-dashboard")
    public String handle_post1(@RequestParam("task-type") String tsk_status, Model model,Pageable pageable) {
        int LoggedUserId=(int) registerService.logged_user().get().getId();

        String username = registerService.logged_user().get().getFullName();
        int groupId = registerService.logged_user().get().getGroups().stream().filter(g->g.getStatus().equals("active")).findAny().get().getId();
        log.info(String.format("POST -> /group-dashboards -> %s in group with id : %d",  tsk_status,groupId));
        MyGroup myGroup = groupRepo.findById(groupId).get();
        List<MyUser> allByGroups = myUserRepo.findAllByGroups(myGroup);
        model.addAttribute("members",allByGroups);

        registerService.addProfile(model);

        switch (tsk_status){
            case "overdue":
                model.addAttribute("tasksOfGroup", groupDashService.pageForOverdue(pageable,groupId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                model.addAttribute("members",allByGroups);
                break;
            case "today":
                model.addAttribute("tasksOfGroup", groupDashService.pageForToday(pageable,groupId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                model.addAttribute("members",allByGroups);
                break;
            case "done":
                model.addAttribute("tasksOfGroup", groupDashService.pageForDone(pageable,groupId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                model.addAttribute("members",allByGroups);
                break;
            case "available":
                model.addAttribute("tasksOfGroup", groupDashService.fetchTasksByGroupId(pageable,groupId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                model.addAttribute("members",allByGroups);
                break;

            case "important":
                model.addAttribute("tasksOfGroup", groupDashService.pageForImportant(pageable,groupId));
                model.addAttribute("username", username);
                registerService.addProfile(model);
                model.addAttribute("members",allByGroups);
                break;
        }


        return "group-dashboard";
    }

//
//    PostMapping("send")
//        public RedirectView chat_post
}
