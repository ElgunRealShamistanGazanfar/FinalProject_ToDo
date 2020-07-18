package app.controller;


import app.entity.MyUser;
import app.repo.MyUserRepo;
import app.repo.TaskRepo;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping("/")
public class RegisterController {

    private final MyUserRepo myUserRepo;
    private final TaskRepo taskRepo;
    private final RegisterService registerService;
    private final TaskService taskService;

    public RegisterController(MyUserRepo myUserRepo, TaskRepo taskRepo, RegisterService registerService, TaskService taskService) {
        this.myUserRepo = myUserRepo;
        this.taskRepo = taskRepo;
        this.registerService = registerService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String handle_root() {
        log.info("getMapping -> /  (root)");
        return "login";
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

            MyUser new_user = new MyUser(full_name,email,email,password,"USER");
            rs.addProfileAndSaveToDB(new_user, imageFile);
            model.addAttribute("suc_reg","You have successfully registered");
            request.setAttribute("JSESSIONID", new_user);
            return "sign-up";
        }
        return "error_repas";

    }

}
