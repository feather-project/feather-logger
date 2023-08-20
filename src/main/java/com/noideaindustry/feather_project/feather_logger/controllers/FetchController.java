package com.noideaindustry.feather_project.feather_logger.controllers;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import com.noideaindustry.feather_project.feather_logger.models.DefaultLogModel;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import com.noideaindustry.feather_project.feather_logger.utils.RequestUtils;
import com.noideaindustry.feather_project.feather_logger.utils.ResponseUtils;
import spark.Spark;
import spark.route.HttpMethod;

public class FetchController extends Controller {
    public FetchController() {
        super("/logs", HttpMethod.get);
    }

    @Override
    public void initialize() {
        Spark.get(super.getFull(), (request, response) -> {
            try {
                final var query = RequestUtils.getQuery(request.queryString());
                if (query == null) return ResponseUtils.badRequest("Request query is not valid.");

                final String fileId = query.get("fileId").getAsString();
                final var models = FileManager.readLines(fileId).stream().map(DefaultLogModel::new).toList();

                final var jsonObject = new JsonObject();
                jsonObject.add("logs", ConstantUtils.GSON.toJsonTree(models.stream().map(DefaultLogModel::getAsJson).toList()));

                return ResponseUtils.ok("Successfully fetched '%d' logs in specified file.".formatted(models.size()), jsonObject);
            } catch (Exception e) {
                return ResponseUtils.internalServerError(e.getMessage());
            }
        });
    }
}
