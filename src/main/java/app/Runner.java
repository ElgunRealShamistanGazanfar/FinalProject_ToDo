package app;


import app.repo.MyUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.Arrays;

@SpringBootApplication
@Log4j2
@EnableOAuth2Sso
public class Runner extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure( HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()

                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .permitAll();

    }
    @RequestMapping("/user")
    public Principal user (Principal principal){
        MyUserRepo myUserRepo;
        log.info(Arrays.toString(principal.getClass().getFields()));
        return principal;
    }
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
