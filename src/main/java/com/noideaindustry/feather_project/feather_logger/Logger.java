package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.listeners.FileObserver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@EnableWebMvc
@SpringBootApplication
public class Logger {
    private static final Logger _instance = new Logger();
    private final Map<String, FileObserver> observers = new HashMap<>();

    public static Logger get() {
        return _instance;
    }

    public Map<String, FileObserver> getObservers() {
        return this.observers;
    }

    public FileObserver getObserver(final String key) {
        return this.observers.get(key);
    }
}
