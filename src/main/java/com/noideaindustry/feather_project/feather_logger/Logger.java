package com.noideaindustry.feather_project.feather_logger;

import com.noideaindustry.feather_project.feather_logger.managers.SessionManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableScheduling

public class Logger {
    private static final Logger _instance = new Logger();
    public static Logger get() {
        return _instance;
    }

    private final SessionManager sessionManager;

    public Logger() {
        this.sessionManager = new SessionManager();
    }

    @Scheduled(fixedDelay = 30 * 1000L)
    public void heartbeat() {
        Logger.get().getSessionManager().heartbeat();
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}
