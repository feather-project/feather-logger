package com.noideaindustry.feather_project.feather_logger.managers;

import com.noideaindustry.feather_project.feather_logger.listeners.FileObserver;

import java.util.HashMap;
import java.util.Map;

public class ObserverManager {
    private final Map<String, FileObserver> observers = new HashMap<>();

    public Map<String, FileObserver> getObservers() {
        return this.observers;
    }

    public FileObserver getObserver(final String key) {
        return this.observers.get(key);
    }
}
