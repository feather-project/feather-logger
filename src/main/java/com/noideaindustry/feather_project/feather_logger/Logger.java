package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.managers.SessionManager;

public class Logger {
    private static final Logger _instance = new Logger();
    public static Logger get() {
        return _instance;
    }

    private final SessionManager sessionManager;

    public Logger() {
        this.sessionManager = new SessionManager();
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}
