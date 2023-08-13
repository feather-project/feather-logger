package com.noideaindustry.feather_project.feather_logger.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;

public class RequestUtils {
    public static JsonObject getBody(final String body) {
        try { return JsonParser.parseString(body).getAsJsonObject(); }
        catch (Exception e) { return null; }
    }

    public static JsonObject getQuery(final String query) {
        try {
            final var jsonObject = new JsonObject();
            final var params = query.split("&");
            final var list = Arrays.stream(params).map(e -> e.split("=")).toList();

            for(final var element : list) {
                jsonObject.addProperty(element[0], element[1]);
            }

            return jsonObject;
        }
        catch (Exception e) { return null; }
    }
}
