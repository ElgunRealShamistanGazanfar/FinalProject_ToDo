package app.controller;


import app.entity.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("login")
    public String handle_get1() {

        log.info("GET -> login");
        return "login";
    }
}
