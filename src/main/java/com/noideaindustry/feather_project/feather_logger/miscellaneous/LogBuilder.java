package com.noideaindustry.feather_project.feather_logger.miscellaneous;

import com.noideaindustry.feather_project.feather_logger.models.LogModel;

public class LogBuilder {
    public static LogModel error(final String message) {
        return new LogModel(message, LogLevel.ERROR);
    }

    public static LogModel warn(final String message) {
        return new LogModel(message, LogLevel.WARN);
    }

    public static LogModel info(final String message) {
        return new LogModel(message, LogLevel.INFO);
    }

    public static LogModel debug(final String message) {
        return new LogModel(message, LogLevel.DEBUG);
    }

    public static LogModel custom(final String message, final LogLevel level) {
        return new LogModel(message, level);
    }

    public static LogModel fromInput(final String input, final String message) {
        return switch (input.toLowerCase()) {
            case "error" -> error(message);
            case "warn" -> warn(message);
            case "info" -> info(message);
            case "debug" -> debug(message);
            default -> custom(message, LogLevel.fromInput(input));
        };
    }
}
