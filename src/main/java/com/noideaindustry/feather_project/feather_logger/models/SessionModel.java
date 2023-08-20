package com.noideaindustry.feather_project.feather_logger.models;

import org.eclipse.jetty.websocket.api.Session;

public class SessionModel {
    private String fileId = "";
    private final Session stream;

    public SessionModel(final Session session) {
        this.stream = session;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    public Session getStream() {
        return this.stream;
    }
}
