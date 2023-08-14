package com.noideaindustry.feather_project.feather_logger.managers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionManager {
    private final Map<String, SseEmitter> sessions = new HashMap<>();

    public void addSession(final String id, final SseEmitter session) {
        this.sessions.put(id, session);
        System.out.println("Added sse session.");
    }

    public void removeSession(final String id) {
        this.sessions.remove(id);
        System.out.println("Removed sse session.");
    }

    public SseEmitter getSession(final String id) {
        return this.sessions.get(id);
    }

    public List<SseEmitter> getSessions() {
        return this.sessions.values().stream().toList();
    }

    public void broadcast(final JsonObject jsonObject) {
        this.sessions.forEach((k, v) -> {
            try { v.send(ConstantUtils.GSON.toJson(jsonObject)); }
            catch (IOException ignored) { }
        } );
    }
}

