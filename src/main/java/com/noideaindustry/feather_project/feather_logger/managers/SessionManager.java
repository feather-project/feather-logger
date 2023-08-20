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

    public SessionManager() {
        ConstantUtils.SCHEDULE.scheduleAtFixedRate(this::heartbeat, 45, 45, TimeUnit.SECONDS);
    }

    public void add(final String clientId, final SessionModel model) {
        this.sessions.put(clientId, model);
    }

    public void remove(final String clientId) {
        this.sessions.remove(clientId);
    }

    public SessionModel getById(final String clientId) {
        return this.sessions.get(clientId);
    }

    public boolean isClosed(final String clientId) {
        return getById(clientId).getStream().isOpen();
    }

    public void send(final Session stream, final String content) {
        try { stream.getRemote().sendString(content); }
        catch (Exception e) { System.out.printf("Sending content failed at '%s'%n", stream.getLocalAddress()); }
    }

    public void send(final Session stream, final JsonObject payload) {
        this.send(stream, ConstantUtils.GSON.toJson(payload));
    }

    public void broadcast(final JsonObject payload) {
        this.sessions.forEach((clientId, stream) -> this.send(stream.getStream(), payload));
    }

    public void broadcast(final String content) {
        this.sessions.forEach((clientId, stream) -> this.send(stream.getStream(), content));
    }

    public void disconnect(final String clientId, final int code, final String reason, final String error) {
        try (final var stream = this.sessions.remove(clientId).getStream()) {
            final var payload = new JsonObject();
            payload.addProperty("code", code);
            payload.addProperty("reason", reason);
            if (error != null) payload.addProperty("error", error);

            this.send(stream, payload);
        }
    }

    private void heartbeat() {
        final int size = this.sessions.size();
        if(size == 0) return;

        System.out.printf("Sending heartbeat signal to '%d' sessions.%n", size);
        this.broadcast("Heartbeat");
    }
}

