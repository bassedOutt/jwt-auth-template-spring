package com.example.jwt_auth_template;

import com.example.jwt_auth_template.entity.User;
import com.example.jwt_auth_template.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

import static com.example.jwt_auth_template.util.Util.ROLE_ADMIN;
import static com.example.jwt_auth_template.util.Util.ROLE_USER;

@SpringBootApplication
@RequiredArgsConstructor

public class JwtAuthTemplateApplication implements CommandLineRunner {


    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.saveRole(ROLE_ADMIN);
        userService.saveRole(ROLE_USER);

        userService.insert( (new User("Denzel","Curry","curry@gmail.com","1234")));
        userService.insert( (new User("Denzel","Washington","washington@gmail.com","1234")));
        userService.insert( (new User("Freddie","Mercury","mercury@gmail.com","1234")));

        userService.addRoleToUser("mercury@gmail.com",ROLE_ADMIN);
    }
}
