package com.noideaindustry.feather_project.feather_logger.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.Logger;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogBuilder;
import com.noideaindustry.feather_project.feather_logger.models.LogModel;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import com.noideaindustry.feather_project.feather_logger.utils.RequestUtils;
import com.noideaindustry.feather_project.feather_logger.utils.ResponseUtils;

import spark.Spark;
import spark.route.HttpMethod;

public class RegisterController extends Controller {
    public RegisterController() {
        super("/logs", HttpMethod.post);
    }

    @Override
    public void initialize() {
        Spark.post(super.getFull(), (request, response) -> {
            try {
                final var payload = RequestUtils.getBody(request.body());
                if (payload == null) return ResponseUtils.badRequest("Request payload is not valid.");

                final var fileId = payload.get("fileId").getAsString();
                final var element = payload.get("logs").getAsJsonArray();

                final var logs = element.asList().stream()
                        .map(JsonElement::getAsJsonObject)
                        .map(log -> LogBuilder.fromInput(log.get("level").getAsString(), log.get("message").getAsString()))
                        .toList();

                FileManager.writeLines(fileId, logs.stream().map(LogModel::getAsLine).toList());

                ConstantUtils.ASYNC.execute(() -> {
                    final var jsonObject = new JsonObject();
                    jsonObject.addProperty("size", logs.size());
                    jsonObject.add("logs", ConstantUtils.GSON.toJsonTree(logs.stream().map(LogModel::getAsJson).toList()));
                    Logger.get().getSessionManager().broadcast(fileId, jsonObject);
                });

                return ResponseUtils.ok("Successfully written '%d' logs in specified file.".formatted(logs.size()));
            } catch (Exception e) {
                return ResponseUtils.internalServerError(e.getMessage());
            }
        });
    }
}
