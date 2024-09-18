package com.faithjoyfundation.autopilotapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAsync
@SpringBootApplication
public class AutoPilotApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoPilotApiApplication.class, args);
    }

}
