package com.noideaindustry.feather_project.feather_logger.managers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private static final String root = System.getProperty("user.dir");
    private static final String id = "cache";

    static {
        try { Files.createDirectories(Paths.get(root, id));}
        catch (IOException e) { throw new RuntimeException(e); }
    }
    public static Path createFile(final String name) throws IOException {
        final var path = Paths.get(root, id, name);
        if(Files.exists(path)) return path;
        return Files.createFile(path);
    }

    public static void deleteFile(final String name) throws IOException {
        final var path = Paths.get(root, id, name);
        if(!Files.exists(path)) return;
        Files.delete(path);
    }

    public static Path getFile(final String name) {
        return Paths.get(root, id, name);
    }

    public static List<String> readLines(final File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }
    public static List<String> readLines(final String name) throws IOException {
        return readLines(Paths.get(root, id, name).toFile());
    }
}
