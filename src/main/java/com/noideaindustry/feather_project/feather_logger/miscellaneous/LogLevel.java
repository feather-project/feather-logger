package com.noideaindustry.feather_project.feather_logger.miscellaneous;

import com.noideaindustry.feather_project.feather_logger.utils.ConsoleColor;

public class LogLevel {
    public static LogLevel ERROR = new LogLevel("Error", ConsoleColor.RED);
    public static LogLevel WARN = new LogLevel("Warn", ConsoleColor.YELLOW);
    public static LogLevel INFO = new LogLevel("Info", ConsoleColor.BLUE);
    public static LogLevel DEBUG = new LogLevel("Debug", ConsoleColor.PURPLE);

    private final String level;
    private final ConsoleColor color;

    private LogLevel(final String level, final ConsoleColor color) {
        this.level = level;
        this.color = color;
    }

    public ConsoleColor getColor() {
        return this.color;
    }

    public String getLevel() {
        return this.level;
    }

    public static LogLevel fromInput(final String input) {
        return switch (input.toLowerCase()) {
            case "error" -> LogLevel.ERROR;
            case "warn" -> LogLevel.WARN;
            case "info" -> LogLevel.INFO;
            case "debug" -> LogLevel.DEBUG;
            default -> new LogLevel("Unknown -> %s".formatted(input), ConsoleColor.WHITE);
        };
    }
}
