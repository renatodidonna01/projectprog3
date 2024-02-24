package com.example.projectTwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.projectTwitter")
public class ProjectTwitter {
    public static void main(String[] args) {
        SpringApplication.run(ProjectTwitter.class, args);
    }
    
}
