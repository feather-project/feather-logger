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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logger")
public class RegisterController {
    @PostMapping("/logs")
    public String registerLogs(@RequestBody final String body) {
        try {
            final var payload = RequestUtils.getBody(body);

            final var uuid = payload.get("uuid").getAsString();
            final var element = payload.get("logs").getAsJsonArray();

            final var logs = element.asList().stream()
                .map(JsonElement::getAsJsonObject)
                .map(log -> LogBuilder.fromInput(log.get("level").getAsString(), log.get("message").getAsString()))
                .toList();

            FileManager.writeFile(uuid, logs.stream().map(LogModel::getAsLine).toList());

            ConstantUtils.ASYNC.execute(() -> {
                final var jsonObject = new JsonObject();
                jsonObject.add("logs", ConstantUtils.GSON.toJsonTree(logs.stream().map(LogModel::getAsJson).toList()));
                Logger.get().getSessionManager().broadcast(uuid, jsonObject);
            });

            return ResponseUtils.ok("Registered logs successfully.");
        } catch (Exception e) {
            return ResponseUtils.internalServerError(e.getLocalizedMessage());
        }
    }
}
