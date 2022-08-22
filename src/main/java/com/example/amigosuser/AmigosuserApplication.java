package com.example.amigosuser;

import com.example.amigosuser.domain.Account;
import com.example.amigosuser.domain.Role;
import com.example.amigosuser.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AmigosuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigosuserApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(AccountService accountService) {
        return args -> {
            accountService.createRole(new Role(null, "ROLE_USER"));
            accountService.createRole(new Role(null, "ROLE_ADMIN"));

            accountService.createAccount(new Account(null, "Test Nickname", "1234", new ArrayList<>()));

            accountService.addRoleToAccount("Test Nickname", "ROLE_USER");
        };
    }
}
