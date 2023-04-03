package com.charter.charterdemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CharterDemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CharterDemoAppApplication.class, args);
    }
}
