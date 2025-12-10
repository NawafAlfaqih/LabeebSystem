package org.example.labeebsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling

@SpringBootApplication
public class LabeebSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabeebSystemApplication.class, args);
    }

}
