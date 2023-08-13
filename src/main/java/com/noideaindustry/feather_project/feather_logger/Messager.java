package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.miscellaneous.MessageLevel;
import com.noideaindustry.feather_project.feather_logger.models.MessageModel;

public class Messager {
    public static MessageModel info(final String message) {
        return new MessageModel(message, MessageLevel.INFO);
    }

    public static MessageModel error(final String message) {
        return new MessageModel(message, MessageLevel.ERROR);
    }

    public static MessageModel warn(final String message) {
        return new MessageModel(message, MessageLevel.WARN);
    }

    public static MessageModel debug(final String message) {
        return new MessageModel(message, MessageLevel.DEBUG);
    }

    public static MessageModel custom(final String message, final MessageLevel level) {
        return new MessageModel(message, level);
    }
}
