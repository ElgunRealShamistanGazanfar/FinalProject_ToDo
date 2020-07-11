package app;

import app.repo.MyUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Runner{
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
