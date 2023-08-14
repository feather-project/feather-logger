package com.noideaindustry.feather_project.feather_logger.miscellaneous;

import com.noideaindustry.feather_project.feather_logger.models.DefaultLogModel;
import com.noideaindustry.feather_project.feather_logger.models.LogModel;

public class LogBuilder {
    public static LogModel fromInput(final String input, final String message) {
        return switch (input.toLowerCase()) {
            case "error" -> new DefaultLogModel(message, LogLevel.ERROR);
            case "warn" -> new DefaultLogModel(message, LogLevel.WARN);
            case "info" -> new DefaultLogModel(message, LogLevel.INFO);
            case "debug" -> new DefaultLogModel(message, LogLevel.DEBUG);
            default -> new DefaultLogModel(message, LogLevel.fromInput(input));
        };
    }
}
