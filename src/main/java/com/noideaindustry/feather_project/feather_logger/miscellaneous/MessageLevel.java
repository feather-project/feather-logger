package com.noideaindustry.feather_project.feather_logger.miscellaneous;

import com.noideaindustry.feather_project.feather_logger.utils.ConsoleColor;

public class MessageLevel {
    public static MessageLevel ERROR = new MessageLevel("Error", ConsoleColor.RED);
    public static MessageLevel WARN = new MessageLevel("Warn", ConsoleColor.YELLOW);
    public static MessageLevel INFO = new MessageLevel("Info", ConsoleColor.BLUE);
    public static MessageLevel DEBUG = new MessageLevel("Debug", ConsoleColor.PURPLE);

    private final String level;
    private final ConsoleColor color;

    private MessageLevel(final String level, final ConsoleColor color) {
        this.level = level;
        this.color = color;
    }

    public static MessageLevel custom(final String level, final ConsoleColor color) {
        return new MessageLevel(level, color);
    }
}
