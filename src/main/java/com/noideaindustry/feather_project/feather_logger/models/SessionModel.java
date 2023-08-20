package com.noideaindustry.feather_project.feather_logger.models;

import org.eclipse.jetty.websocket.api.Session;

public class SessionModel {
    private String subscription;
    private final Session stream;

    public SessionModel(final Session session) {
        this.stream = session;
    }

    public String getSubscription() {
        return this.subscription;
    }

    public void setSubscription(final String subscription) {
        this.subscription = subscription;
    }

    public Session getStream() {
        return this.stream;
    }
}
