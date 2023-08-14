package com.noideaindustry.feather_project.feather_logger.models;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogLevel;

import java.time.Instant;
import java.util.regex.Pattern;

public abstract class LogModel {
    private final long timestamp;
    private final String message;
    private final LogLevel level;

    public LogModel(final long timestamp, final String message, final LogLevel level) {
        this.timestamp = timestamp;
        this.message = message;
        this.level = level;
    }

    public LogModel(final String message, final LogLevel level) {
        this.timestamp = Instant.now().toEpochMilli();
        this.message = message;
        this.level = level;
    }

    public LogModel(final String line) throws Exception {
        final var matcher = this.getPattern().matcher(line);

        if(!matcher.matches()) throw new Exception("M");

       this.timestamp = Long.parseLong(matcher.group(1));
       this.message = matcher.group(4);
       this.level = LogLevel.fromInput(matcher.group(3));
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

    public Pattern getPattern() {
        return Pattern.compile("(\\d{13}) - (\\[(.*?)]) > (.*)");
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public LogLevel getLevel() {
        return this.level;
    }
}
