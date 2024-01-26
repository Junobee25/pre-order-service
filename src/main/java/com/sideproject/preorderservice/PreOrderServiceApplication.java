package com.sideproject.preorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PreOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreOrderServiceApplication.class, args);
    }

}
