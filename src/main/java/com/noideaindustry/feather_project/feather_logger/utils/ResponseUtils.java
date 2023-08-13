package com.noideaindustry.feather_project.feather_logger.utils;

import com.google.gson.JsonObject;

import java.time.Instant;

public class ResponseUtils {
    private static String template(final int code, final String status, final String message, final JsonObject jsonObject) {
        final JsonObject responseObject = new JsonObject();

        responseObject.addProperty("timestamp", Instant.now().toEpochMilli());
        responseObject.addProperty("status", status);
        responseObject.addProperty("code", code);
        responseObject.addProperty("message", message);

        if (jsonObject != null) {
            responseObject.add("content", jsonObject);
        } else {
            responseObject.addProperty("content", "null");
        }

        return responseObject.toString();
    }

    public static String internalServerError(final String message, final JsonObject jsonObject) {
        return template(500, "INTERNAL_SERVER_ERROR", message, jsonObject);
    }
    public static String internalServerError(final String message) {
        return internalServerError(message, null);
    }

    public static String notFound(final String message, final JsonObject jsonObject) {
        return template(401, "NOT_FOUND", message, jsonObject);
    }
    public static String notFound(final String message) {
        return notFound(message, null);
    }

    public static String badRequest(final String message, final JsonObject jsonObject) {
        return template(400, "BAD_REQUEST", message, jsonObject);
    }
    public static String badRequest(final String message) {
        return badRequest(message, null);
    }

    public static String ok(final String message, final JsonObject jsonObject) {
        return template(200, "OK", message, jsonObject);
    }
    public static String ok(final String message) {
        return ok(message, null);
    }

    public static String unauthorized(final String message, final JsonObject jsonObject) {
        return template(401, "UNAUTHORIZED", message, jsonObject);
    }
    public static String unauthorized(final String message) {
        return unauthorized(message, null);
    }
}
