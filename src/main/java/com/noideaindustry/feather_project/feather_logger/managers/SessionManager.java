package com.noideaindustry.feather_project.feather_logger.managers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.models.SessionModel;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SessionManager {
    private final Map<String, SessionModel> sessions = new ConcurrentHashMap<>();
    private final int delay = 45;

    public SessionManager() {
        ConstantUtils.SCHEDULE.scheduleAtFixedRate(this::heartbeat, delay, delay, TimeUnit.SECONDS);
        System.out.printf("Scheduled heartbeat signal every '%d' seconds.%n", delay);
    }

    public void add(final String clientId, final SessionModel model) {
        this.sessions.put(clientId, model);
    }

    public void remove(final String clientId) {
        this.sessions.remove(clientId);
    }

    public SessionModel get(final String clientId) {
        return this.sessions.get(clientId);
    }

    public boolean isClosed(final String clientId) {
        return get(clientId).getStream().isOpen();
    }

    private void send(final Session stream, final String content) {
        try { stream.getRemote().sendString(content); }
        catch (Exception e) { System.out.printf("Sending content failed at '%s'%n", stream.getLocalAddress()); }
    }

    public void send(final Session stream, final String event, final JsonObject payload) {
        payload.addProperty("event", event);
        this.send(stream, ConstantUtils.GSON.toJson(payload));
    }

    public void send(final Session stream, final String event, final String message) {
        final var payload = new JsonObject();
        payload.addProperty("event", event);
        payload.addProperty("message", message);
        this.send(stream, ConstantUtils.GSON.toJson(payload));
    }

    public void broadcast(final JsonObject payload) {
        this.sessions.forEach((clientId, session) -> this.send(session.getStream(), "broadcast", payload));
    }

    public void broadcast(final String content) {
        this.sessions.forEach((clientId, session) -> this.send(session.getStream(), content));
    }

    public void broadcast(final String fileId, final JsonObject content) {
        final var found = this.sessions.values()
            .stream()
            .filter(session -> session.getFileId().equals(fileId))
            .toList();

        found.forEach(session -> this.send(session.getStream(), "broadcast", content));
    }

    public void disconnect(final String clientId, final int code, final String reason, final String error) {
        try (final var stream = this.sessions.remove(clientId).getStream()) {
            final var payload = new JsonObject();
            payload.addProperty("code", code);
            payload.addProperty("reason", reason);
            payload.addProperty("error", error);

            this.send(stream,"disconnect", payload);
        }
    }

    private void heartbeat() {
        final int size = this.sessions.size();
        if(size == 0) return;
        this.broadcast("Heartbeat");
    }
}

