package com.example.userservicefeb25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.example.userservicefeb25.repository")
@SpringBootApplication
@EnableJpaAuditing()
public class UserServiceFeb25Application {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceFeb25Application.class, args);
    }

}
