package com.noideaindustry.feather_project.feather_logger.utils;

public enum ConsoleColor {
    CLEAR("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String color;

    ConsoleColor(final String color) {
        this.color = color;
    }

    public String get() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.get();
    }
}
