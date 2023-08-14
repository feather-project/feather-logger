package com.noideaindustry.feather_project.feather_logger.miscellaneous;

public class LogLevel {
    public static LogLevel ERROR = new LogLevel("Error");
    public static LogLevel WARN = new LogLevel("Warn");
    public static LogLevel INFO = new LogLevel("Info");
    public static LogLevel DEBUG = new LogLevel("Debug");

    private final String name;

    private LogLevel(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static LogLevel fromInput(final String input) {
        return switch (input.toLowerCase()) {
            case "error" -> LogLevel.ERROR;
            case "warn" -> LogLevel.WARN;
            case "info" -> LogLevel.INFO;
            case "debug" -> LogLevel.DEBUG;
            default -> new LogLevel(input);
        };
    }
}
