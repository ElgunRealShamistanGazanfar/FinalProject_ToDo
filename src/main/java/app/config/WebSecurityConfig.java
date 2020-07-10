package app.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Log4j2
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    public WebSecurityConfig(MvcConfig mvcConfig) {
//        this.mvcConfig = mvcConfig;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/sign-up","/resources/**").permitAll()
                .antMatchers("/forgot").permitAll()
                .anyRequest().
                authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/landing")
                .permitAll()
                .and()
                .oauth2Login().loginPage("/login")
                .defaultSuccessUrl("/landing")
                .and()
                .logout()
                .permitAll();




        http.csrf().disable();


    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .successForwardUrl("/landing")
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//        .oauth2Login()
//
//        ;
//
//
//
//        http.headers().frameOptions().sameOrigin();
//
//    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }}

//
//    private final MvcConfig mvcConfig;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(mvcConfig.dataSource())
//                .usersByUsernameQuery(
//                        "select username,password, enabled from users where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from users where username=?")
//                .withUser(User.withUsername("jamal").password("jamal").roles("USER"));
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}

