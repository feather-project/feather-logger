package com.noideaindustry.feather_project.feather_logger.models;

import com.noideaindustry.feather_project.feather_logger.miscellaneous.MessageLevel;

public class MessageModel {
    private final String message;
    private final MessageLevel level;

    public MessageModel(final String message, final MessageLevel level) {
        this.message = message;
        this.level = level;
    }
}
