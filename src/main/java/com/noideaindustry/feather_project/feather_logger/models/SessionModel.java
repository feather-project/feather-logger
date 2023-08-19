package com.noideaindustry.feather_project.feather_logger.models;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SessionModel {
    private final SseEmitter emitter;
    private final String uuid;
    private String id;

    public SessionModel(final String uuid, final SseEmitter emitter) {
        this.emitter = emitter;
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public SseEmitter getEmitter() {
        return this.emitter;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
