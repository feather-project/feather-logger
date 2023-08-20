package com.noideaindustry.feather_project.feather_logger.websocket;

import com.google.gson.JsonObject;

public class SocketPayload {
    private final String fileId;
    private final String request;
    private final JsonObject content;

    public SocketPayload(final String fileId, final String request, final JsonObject content) {
        this.fileId = fileId;
        this.request = request;
        this.content = content;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getRequest() {
        return this.request;
    }

    public JsonObject getContent() {
        return this.content;
    }
}
