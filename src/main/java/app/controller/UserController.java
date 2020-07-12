package app.controller;


import app.entity.Task;
import app.entity.Users;
import app.repo.MyUserRepo;
import app.repo.TaskRepo;
import app.service.RegisterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {

    private final MyUserRepo myUserRepo;
    private final TaskRepo taskRepo;

    public UserController(MyUserRepo myUserRepo, TaskRepo taskRepo) {
        this.myUserRepo = myUserRepo;
        this.taskRepo = taskRepo;
    }


    @GetMapping("login")
    public String handle_get222() {
        log.info("getMapping -> /login");
        return "login";
    }


    @GetMapping("landing")
    public String handle_get2(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        List<Task> all = taskRepo.findAll();
        Task random_task = taskRepo.findAll().get((int) (Math.random() * all.size()));
        model.addAttribute("username", username);
        model.addAttribute("title", random_task.getTitle());
        model.addAttribute("content", random_task.getContent());
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
                               HttpServletRequest request,
                               Model model
                               ) {
        RegisterService rs = new RegisterService(myUserRepo);

        log.info("POST -> /sign-up");
        if (password.equals(repassword) && !rs.hasUsed(email)){

            Users new_user = new Users(full_name,email,email,password,"USER");
            myUserRepo.save(new_user);
            model.addAttribute("suc_reg","You have successfully registered");
            request.setAttribute("JSESSIONID", new_user);
            return "sign-up";
        }
        return "error_repas";

    }

}
