package app.controller;


import app.entity.Task;
import app.entity.MyUser;
import app.repo.MyUserRepo;
import app.repo.TaskRepo;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {

    private final MyUserRepo myUserRepo;
    private final TaskRepo taskRepo;
    private final RegisterService registerService;
    private final TaskService taskService;

    public UserController(MyUserRepo myUserRepo, TaskRepo taskRepo, RegisterService registerService, TaskService taskService) {
        this.myUserRepo = myUserRepo;
        this.taskRepo = taskRepo;
        this.registerService = registerService;
        this.taskService = taskService;
    }


    @GetMapping("login")
    public String handle_get222() {
        log.info("getMapping -> /login");
        return "login";
    }


    @GetMapping("showProfile")
    public void showProfileDB(HttpServletResponse response) throws IOException {

        MyUser user = registerService.logged_user();

        if (user.getProfile() != null) {
            byte[] byteArray = new byte[user.getProfile().length];

            int i = 0;
            for (Byte imgByte : user.getProfile()) {
                byteArray[i++] = imgByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }

    }

    //afteer login page it comes
    @GetMapping("landing")
    public String handle_get2(Model model) {
        Task random_task = new Task();

        long logged_user_id = registerService.logged_user().getId();
        List<Task> all = (List<Task>) taskService.fetchAll((int) logged_user_id);
        if (!all.isEmpty()) {
            random_task = all.get((int) (Math.random() * all.size()));
        } else {
            random_task.setContent("GO Dashboard to add new tasks");
            random_task.setTitle("No Task");
        }

        model.addAttribute("username", registerService.logged_user().getFullName());
        model.addAttribute("title", random_task.getTitle());
        model.addAttribute("content", random_task.getContent());

        registerService.addProfile(model);
        log.info("GET -> /landing");
        return "landing";
    }

    @PostMapping("landing")
    public String handle_post2() {


        log.info("Post -> /landing");
        return "landing";
    }


}
