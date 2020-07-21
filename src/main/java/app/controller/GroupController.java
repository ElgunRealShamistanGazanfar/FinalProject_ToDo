package app.controller;

import app.entity.MyGroup;
import app.entity.MyUser;
import app.entity.Task;
import app.repo.GroupRepo;
import app.repo.MyUserRepo;
import app.repo.TaskRepo;
import app.service.GroupDashService;
import app.service.GroupService;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class GroupController {

    private final MyUserRepo myUserRepo;
    private final TaskRepo taskRepo;
    private final GroupRepo groupRepo;
    private final RegisterService registerService;
    private final TaskService taskService;
    private final GroupService groupService;
    private final GroupDashService groupDashService;

    public GroupController(MyUserRepo myUserRepo, TaskRepo taskRepo, GroupRepo groupRepo, RegisterService registerService, TaskService taskService, GroupService groupService, GroupDashService groupDashService) {
        this.myUserRepo = myUserRepo;
        this.taskRepo = taskRepo;
        this.groupRepo = groupRepo;
        this.registerService = registerService;
        this.taskService = taskService;
        this.groupService = groupService;
        this.groupDashService = groupDashService;
    }



    @GetMapping("add-group")
    public String handle_get_group(Model model) {
        model.addAttribute("myGroups", registerService.logged_user().get().getGroups());
        log.info("GET -> /add-group");
        return "add-group";
    }
    @PostMapping("search-group")
    public String handle_post_search(@RequestParam("group")String groupName, Model model) {
        List<MyGroup> groups = groupRepo.findAllByGroupNameIsLike(groupName);
        model.addAttribute("myGroups", registerService.logged_user().get().getGroups());
            model.addAttribute("groups", groups);
        log.info("Post -> /search-group");

        return "add-group";

    }

    @GetMapping("join/{id}")
    public String join_group(@PathVariable int id, Model model){
        groupService.AddUserToGroup(id, model);
        model.addAttribute("myGroups", registerService.logged_user().get().getGroups());
        log.info(String.format("You joined to the group with id %d", id));
        return "add-group";

    }

    @GetMapping("go/{groupId}")
    public String go_group(@PathVariable int groupId, Model model, Pageable pageable){
        MyGroup myGroup = groupRepo.findById(groupId).get();
        List<MyUser> allByGroups = myUserRepo.findAllByGroups(myGroup);
        model.addAttribute("members",allByGroups);

        log.info(String.format("Go to the group with id %d", groupId));
        final List<MyGroup> my_groups = registerService.logged_user().get().getGroups();

        for(MyGroup group : my_groups){
            if (group.getId()==groupId){
                group.setStatus("active");
            }
            else {
                group.setStatus("inactive");
            }
        }


        final Page<Task> tasksOfGroup = groupDashService.fetchTasksByGroupId(pageable, groupId);
        model.addAttribute("tasksOfGroup", tasksOfGroup);

        return "group-dashboard";

    }


    @PostMapping("create-group")
    public String handle_post_create(@RequestParam("groupName")String groupName, @RequestParam("groupPass")String groupPass, @RequestParam("groupDesc")String groupDesc,Model model) {
       groupService.createGroup(groupName,groupPass, groupDesc);
        model.addAttribute("myGroups", registerService.logged_user().get().getGroups());
      //  model.addAttribute("createMes", String.format("You have successfully created %s group, with description %s", groupName, groupDesc));
        log.info("Post -> /create-group");

        return "add-group";

    }

    @GetMapping("group-dashboard")
    public String groupDash(){
        return "group-dashboard";

    }


}