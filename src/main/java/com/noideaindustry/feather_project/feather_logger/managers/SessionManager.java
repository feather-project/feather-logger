package com.noideaindustry.feather_project.feather_logger.managers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, Map<String, SseEmitter>> sessions = new ConcurrentHashMap<>();

    public void addSession(final String uuid, final String id, final SseEmitter session) {
        session.onCompletion(() -> {
            this.removeSession(uuid, id);
        });

        this.sessions.putIfAbsent(uuid, new HashMap<>());
        this.sessions.get(uuid).put(id, session);
    }

    public void removeSession(final String uuid, final String id) {
        this.sessions.get(uuid).remove(id);
    }

    public void broadcast(final String uuid, final JsonObject jsonObject) {
        final var matched = this.sessions.get(uuid);
        if(matched == null) return;

        matched.values().forEach(session -> {
            try { session.send(ConstantUtils.GSON.toJson(jsonObject)); }
            catch (IOException ignored) { }
        } );
    }

    public void heartbeat() {
        this.sessions.values().forEach(sessions -> sessions.values().forEach(session -> {
            try { session.send(SseEmitter.event().comment("Heartbeat")); }
            catch (IOException e) { session.complete(); }
        }));
    }
}

