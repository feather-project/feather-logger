package com.noideaindustry.feather_project.feather_logger.controllers;

import spark.route.HttpMethod;

public abstract class Controller {
    private final String id = "logger";
    private final String base = "api/v1/";
    private String full = "";
    private String relative = "";
    private final HttpMethod method;

    public Controller(final String endpoint, final HttpMethod method) {
        this.method = method;
        this.setRelative(endpoint);
        this.setup();
    }

    public abstract void initialize();

    void setup() {
        this.full = this.base + this.id + this.relative;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public final String getFull() {
        return this.full;
    }

    public final String getRelative() {
        return this.relative;
    }

    void setRelative(final String endpoint) {
        this.relative = endpoint;
    }
}
