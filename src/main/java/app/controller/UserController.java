package app.controller;


import app.entity.Task;
import app.entity.Users;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping
    public String handle_root() {
        log.info("getMapping -> /  (root)");
        return "login";
    }

    @GetMapping("showProfile")
    public void showProfileDB( HttpServletResponse response) throws IOException {

        Optional<Users> res = registerService.logged_user();

        if (res.isPresent()) {
            Users user = res.get();

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
    }

        //afteer login page it comes
    @GetMapping("landing")
    public String handle_get2(Model model) {
        Task random_task = new Task();

        long logged_user_id = registerService.logged_user().get().getId();
        List<Task> all = (List<Task>) taskService.fetchAll((int)logged_user_id);
        if (!all.isEmpty()){
          random_task = all.get((int) (Math.random() * all.size()));
        }else {
            random_task.setContent("GO Dashboard to add new tasks");
            random_task.setTitle("No Task");
        }

        model.addAttribute("username", registerService.logged_user().get().getFullName());
        model.addAttribute("title", random_task.getTitle());
        model.addAttribute("content", random_task.getContent());

       registerService.addProfile(model);
        log.info("GET -> /landing");
        return "landing";
    }
    @GetMapping("reset-password")
    public String handle_get_forgot() {
        log.info("GET -> /reset-password");
        return "reset-password";
    }
    @PostMapping("reset-password")
    public String handle_post_forgot(@RequestParam("full_name_f")String fullname,@RequestParam("email_f")String email
    , Model model) {
        RegisterService rs = new RegisterService(myUserRepo);
        log.info("Post -> /forgot");
        model.addAttribute("user", rs.giveMeUser(fullname, email).get());
        return rs.isCorrect(fullname, email)? "backUp": "login";
    }

    @PostMapping("landing")
    public String handle_post2() {


        log.info("Post -> /landing");
        return "landing";
    }

    @GetMapping("sign-up")
    public String handle_get4() {
        log.info("GET -> /sign-up");
        return "sign-up";
    }

    @PostMapping("sign-up")
    public String handle_post4(@RequestParam("full_name") String full_name,
                                @RequestParam("email")String email,
                               @RequestParam("password")String password,
                               @RequestParam("repassword")String repassword,
                               @RequestParam("myImage") MultipartFile imageFile,
                               HttpServletRequest request,
                               Model model
                               ) {
        RegisterService rs = new RegisterService(myUserRepo);

        log.info("POST -> /sign-up");
        if (password.equals(repassword) && !rs.hasUsed(email)){

            Users new_user = new Users(full_name,email,email,password,"USER");
            rs.addProfileAndSaveToDB(new_user, imageFile);
            model.addAttribute("suc_reg","You have successfully registered");
            request.setAttribute("JSESSIONID", new_user);
            return "sign-up";
        }
        return "error_repas";

    }

}
