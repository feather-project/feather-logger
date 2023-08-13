package com.noideaindustry.feather_project.feather_logger.miscellaneous;

import com.noideaindustry.feather_project.feather_logger.utils.ConsoleColor;

public enum MessageLevel {
    ERROR("Error", ConsoleColor.RED),
    WARN("Warn", ConsoleColor.YELLOW),
    INFO("Info", ConsoleColor.BLUE),
    DEBUG("Debug", ConsoleColor.PURPLE);

    private final String level;
    private final ConsoleColor color;

    MessageLevel(final String level, final ConsoleColor color) {
        this.level = level;
        this.color = color;
    }
}
