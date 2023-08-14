package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.managers.ObserverManager;
import com.noideaindustry.feather_project.feather_logger.managers.SessionManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class Logger {
    private static final Logger _instance = new Logger();
    public static Logger get() {
        return _instance;
    }

    private final SessionManager sessionManager;
    private final ObserverManager observerManager;

    public Logger() {
        this.sessionManager = new SessionManager();
        this.observerManager = new ObserverManager();
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public ObserverManager getObserverManager() {
        return this.observerManager;
    }
}
