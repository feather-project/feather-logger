package com.noideaindustry.feather_project.feather_logger.controllers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import com.noideaindustry.feather_project.feather_logger.models.DefaultLogModel;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import com.noideaindustry.feather_project.feather_logger.utils.ResponseUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/logger")
public class FetchController {
    @GetMapping("/logs")
    public String registerLogs(@RequestParam(name = "uuid") final String uuid) {
        try {
            final var logs = new ArrayList<>();
            for (final String line : FileManager.readLines(uuid)) {
                logs.add(new DefaultLogModel(line).getAsJson());
            }

            final var jsonObject = new JsonObject();
            jsonObject.add("logs", ConstantUtils.GSON.toJsonTree(logs));
            return ResponseUtils.ok("Fetched logs successfully.", jsonObject);
        } catch (Exception e) {
            return ResponseUtils.internalServerError(e.getLocalizedMessage());
        }
    }
}