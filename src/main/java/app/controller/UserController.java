package app.controller;


import app.entity.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {
    @GetMapping("login")
    public String handle_get1() {
        log.info("GET -> /login");
        return "login";
    }

    @PostMapping("login")
    public String handle_post1() {
        log.info("Post -> /login");
        return "login";
    }


    @GetMapping("landing")
    public String handle_get2() {
        log.info("GET -> /landing");
        return "landing";
    }

    @PostMapping("landing")
    public String handle_post2() {
        log.info("Post -> /landing");
        return "landing";
    }

    @GetMapping("tasks-archive")
    public String handle_get3() {
        log.info("GET -> /tasks-archive");
        return "tasks-archive";
    }

}
