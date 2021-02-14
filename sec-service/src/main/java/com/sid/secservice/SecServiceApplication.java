package com.sid.secservice;

import com.sid.secservice.entities.AppRole;
import com.sid.secservice.entities.AppUser;
import com.sid.secservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService){
      return args -> {
          accountService.addNewRole(new AppRole(null, "USER"));
          accountService.addNewRole(new AppRole(null, "ADMIN"));
          accountService.addNewRole(new AppRole(null, "CUSTOMER_MANAGER"));
          accountService.addNewRole(new AppRole(null, "PRODUCT_MANAGER"));
          accountService.addNewRole(new AppRole(null, "BILLS_MANAGER"));

          accountService.addNewUser(new AppUser(null,"mohamed","12345",new ArrayList<>()));
          accountService.addNewUser(new AppUser(null,"rachid","12345",new ArrayList<>()));
          accountService.addNewUser(new AppUser(null,"ahmed","12345",new ArrayList<>()));
          accountService.addNewUser(new AppUser(null,"bouchra","12345",new ArrayList<>()));
          accountService.addNewUser(new AppUser(null,"hamid","12345",new ArrayList<>()));

          accountService.addRoleToUser("mohamed","USER");
          accountService.addRoleToUser("mohamed","ADMIN");
          accountService.addRoleToUser("mohamed","PRODUCT_MANAGER");
          accountService.addRoleToUser("rachid","USER");
          accountService.addRoleToUser("rachid","CUSTOMER_MANAGER");
          accountService.addRoleToUser("ahmed","USER");
          accountService.addRoleToUser("ahmed","PRODUCT_MANAGER");
          accountService.addRoleToUser("ahmed","CUSTOMER_MANAGER");
          accountService.addRoleToUser("hamid","BILLS_MANAGER");
          accountService.addRoleToUser("bouchra","CUSTOMER_MANAGER");
          accountService.addRoleToUser("bouchra","USER");
      };
    }
}
