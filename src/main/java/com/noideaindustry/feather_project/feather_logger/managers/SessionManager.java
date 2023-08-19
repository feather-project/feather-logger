package com.noideaindustry.feather_project.feather_logger.managers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, SseEmitter> sessions = new ConcurrentHashMap<>();

    public void addSession(final String uuid, final SseEmitter session) {
        session.onCompletion(() -> this.removeSession(uuid));
        this.sessions.putIfAbsent(uuid, session);

        System.out.printf("Added session at '%s'%n", uuid);
    }

    public void removeSession(final String uuid) {
        this.sessions.remove(uuid);

        System.out.printf("Removed session at: '%s'%n", uuid);
    }

    public void broadcast(final String uuid, final JsonObject jsonObject) {
        final var session = this.sessions.get(uuid);
        if(session == null) return;

        try { session.send(ConstantUtils.GSON.toJson(jsonObject)); }
        catch (IOException ignored) { }
    }

    public void heartbeat() {
        this.sessions.values().forEach(session -> {
            try { session.send(SseEmitter.event().comment("Heartbeat")); }
            catch (IOException e) { session.complete(); }
        });
    }
}

