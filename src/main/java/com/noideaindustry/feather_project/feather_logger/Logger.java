package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.listeners.FileObserver;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static final Logger _instance = new Logger();
    private final List<FileObserver> observers = new ArrayList<>();

    public static Logger get() {
        return _instance;
    }

    public void start() throws Exception {
        final var file = FileManager.createFile("test.txt").toFile();

        this.observers.add(new FileObserver(file));
        for (final var observer : this.observers) {
            observer.start();
        }

        Thread.sleep(Long.MAX_VALUE);

        for (final var observer : this.observers) {
            observer.stop();
        }
    }
}
