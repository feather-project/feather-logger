package com.noideaindustry.feather_project;

import com.noideaindustry.feather_project.feather_logger.Logger;
import org.springframework.boot.SpringApplication;

import java.util.Collections;

public class Service {
    public static void main(String[] args) {
        Service.serve("127.0.0.1", 10542);
    }

    public static void serve(final String host, final int port) {
        final var app = new SpringApplication(Logger.class);
        app.setDefaultProperties(Collections.singletonMap("server.address", host));
        app.setDefaultProperties(Collections.singletonMap("server.port", String.valueOf(port)));
        app.run();
    }
}
