package com.noideaindustry.feather_project.feather_logger.listeners;

import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.io.IOException;

public class FileListener extends FileAlterationListenerAdaptor {

    private final File file;
    private long size;

    public FileListener(final File file) {
        this.file = file;

        try { this.size = FileManager.readLines(file).size();}
        catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public void onFileChange(final File file) {
        if (!file.equals(this.file)) return;

        System.out.println("File modified: " + file.getName());
        try {
            final var lines = FileManager.readLines(file);
            if (lines.size() <= this.size) return;

            for (int i = (int) this.size; i < lines.size(); i++) {
                System.out.println("Added line: " + lines.get(i));
            }
            this.size = lines.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}