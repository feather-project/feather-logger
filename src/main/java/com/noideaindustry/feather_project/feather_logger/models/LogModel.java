package com.noideaindustry.feather_project.feather_logger.models;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogLevel;

import java.time.Instant;
import java.util.regex.Pattern;

public class LogModel {
    private static final String _pattern = "(\\d{13}) - (\\[(.*?)]) > (.*)";
    private static final Pattern _regex = Pattern.compile(_pattern, Pattern.MULTILINE);

    private final long timestamp;
    private final String message;
    private final LogLevel level;

    public LogModel(final String message, final LogLevel level) {
        this.timestamp = Instant.now().toEpochMilli();
        this.message = message;
        this.level = level;
    }

    public LogModel(final long timestamp, final String message, final LogLevel level) {
        this.timestamp = timestamp;
        this.message = message;
        this.level = level;
    }

    public static LogModel fromLine(final String line) {
        final var matcher = _regex.matcher(line);

        if(!matcher.matches()) return null;

        return new LogModel(
            Long.parseLong(matcher.group(1)),
            matcher.group(4),
            LogLevel.fromInput(matcher.group(3))
        );

    }

    public String getAsLine() {
        return "%d - [%s] > %s".formatted(this.timestamp, this.level.getName(), this.message);
    }

    public JsonObject getAsJson() {
        final var jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", this.timestamp);
        jsonObject.addProperty("message", this.message);
        jsonObject.addProperty("level", this.level.getName());
        return jsonObject;
    }

}
