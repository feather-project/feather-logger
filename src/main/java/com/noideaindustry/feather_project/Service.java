package com.noideaindustry.feather_project;

import com.noideaindustry.feather_project.feather_logger.controllers.Controller;
import com.noideaindustry.feather_project.feather_logger.controllers.RegisterController;
import com.noideaindustry.feather_project.feather_logger.websocket.SocketHandler;
import spark.Spark;

import java.util.ArrayList;

public class Service {
    private final ArrayList<Controller> controllers = new ArrayList<>();

    public Service(final int port) {
        // Setup spark port and websocket path
        Spark.webSocket("/ws/v1/connect", new SocketHandler());
        Spark.port(port);
    }

    private void setup() {
        // Subscribe controllers
        this.controllers.add(new RegisterController());

        // Initialise controllers
        this.controllers.forEach(Controller::initialize);
    }

    public static void main(String[] args) {
        final var service = new Service(10542);
        service.setup();
    }
}
