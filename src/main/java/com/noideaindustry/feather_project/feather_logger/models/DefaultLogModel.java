package com.noideaindustry.feather_project.feather_logger.models;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogLevel;

public class DefaultLogModel extends LogModel {

    public DefaultLogModel(final String message, final LogLevel level) {
        super(message, level);
    }

    public DefaultLogModel(final String line) {
        super(line);
    }

    public DefaultLogModel(final JsonObject jsonObject) {
        super(jsonObject);
    }
}
