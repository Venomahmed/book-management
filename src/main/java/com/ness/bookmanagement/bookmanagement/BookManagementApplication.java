package com.ness.bookmanagement.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableWebSecurity
@SpringBootApplication
public class BookManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
        System.out.println();
        System.out.println("====================");
        System.out.println("Application is Ready");
        System.out.println("====================");
        System.out.println();
    }

}
