package com.noideaindustry.feather_project.feather_logger.models;

import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogLevel;

import java.time.Instant;

public class LogModel {
    private final String message;
    private final LogLevel level;

    public LogModel(final String message, final LogLevel level) {
        this.message = message;
        this.level = level;
    }

    public String getAsLine() {
        return "%d | [%s] - %s".formatted(Instant.now().toEpochMilli(), this.level.getLevel(), this.message);
    }
}
