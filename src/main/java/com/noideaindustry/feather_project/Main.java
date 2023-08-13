package com.noideaindustry.feather_project;

import com.noideaindustry.feather_project.feather_logger.Logger;
import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Logger.class, args);
    }
}