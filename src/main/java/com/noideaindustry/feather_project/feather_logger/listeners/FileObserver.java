package com.noideaindustry.feather_project.feather_logger.listeners;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileObserver {
    private final FileAlterationMonitor monitor;
    private final List<File> files;

    public FileObserver(final File... files) {
        this.files = List.of(files);

        this.monitor = new FileAlterationMonitor(TimeUnit.SECONDS.toMillis(5));

        final var observers = this.files.stream().map(file -> {
            final var observer = new FileAlterationObserver(file.getParentFile());
            observer.addListener(new FileListener(file));
            return observer;
        }).toList();

        observers.forEach(monitor::addObserver);
    }

    public void start() throws Exception {
        this.monitor.start();
    }

    public void stop() throws Exception {
        this.monitor.stop();
    }

    public List<File> getFiles() {
        return this.files;
    }
}
