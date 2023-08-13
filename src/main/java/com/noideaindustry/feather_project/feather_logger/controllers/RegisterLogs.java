package com.noideaindustry.feather_project.feather_logger.controllers;

import com.google.gson.JsonElement;
import com.noideaindustry.feather_project.feather_logger.Logger;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import com.noideaindustry.feather_project.feather_logger.miscellaneous.LogBuilder;
import com.noideaindustry.feather_project.feather_logger.utils.RequestUtils;
import com.noideaindustry.feather_project.feather_logger.utils.ResponseUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logger")
public class RegisterLogs {
    @PostMapping("/log")
    public String registerLog(@RequestBody final String body) {
        try {
            final var jsonObject = RequestUtils.getBody(body);

            final var id = jsonObject.get("id").getAsString();
            final var log = jsonObject.get("log").getAsJsonObject();

            final var line = LogBuilder.fromInput(log.get("level").getAsString(), log.get("message").getAsString()).getAsLine();

            FileManager.writeFile(id, line);

            return ResponseUtils.ok("Registered log successfully.");
        } catch (Exception e) {
            return ResponseUtils.internalServerError(e.getLocalizedMessage());
        }
    }

    @PostMapping("/logs")
    public String registerLogs(@RequestBody final String body) {
        try {
            final var jsonObject = RequestUtils.getBody(body);

            final var id = jsonObject.get("id").getAsString();
            final var logs = jsonObject.get("logs").getAsJsonArray();

            final var lines = logs.asList().stream()
                .map(JsonElement::getAsJsonObject)
                .map(log -> LogBuilder.fromInput(log.get("level").getAsString(), log.get("message").getAsString()).getAsLine())
                .toList();

            FileManager.writeFile(id, lines);

            return ResponseUtils.ok("Registered logs successfully.");
        } catch (Exception e) {
            return ResponseUtils.internalServerError(e.getLocalizedMessage());
        }
    }
}
