package com.noideaindustry.feather_project;

import com.noideaindustry.feather_project.feather_logger.Logger;

public class Main {
    public static void main(String[] args) {
        try { Logger.get().start(); }
        catch (Exception e) { e.printStackTrace(); }
    }
}