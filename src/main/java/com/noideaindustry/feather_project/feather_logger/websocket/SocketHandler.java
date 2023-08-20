package com.noideaindustry.feather_project.feather_logger.websocket;

import com.google.gson.JsonObject;
import com.noideaindustry.feather_project.feather_logger.Logger;
import com.noideaindustry.feather_project.feather_logger.managers.FileManager;
import com.noideaindustry.feather_project.feather_logger.managers.SessionManager;
import com.noideaindustry.feather_project.feather_logger.models.DefaultLogModel;
import com.noideaindustry.feather_project.feather_logger.models.LogModel;
import com.noideaindustry.feather_project.feather_logger.models.SessionModel;
import com.noideaindustry.feather_project.feather_logger.utils.ConstantUtils;
import com.noideaindustry.feather_project.feather_logger.utils.RequestUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.File;
import java.util.List;

@WebSocket
public class SocketHandler {
    private final SessionManager manager = Logger.get().getSessionManager();

    @OnWebSocketConnect
    public void onConnect(final Session stream) {
        try {
            final var query = RequestUtils.getQuery(stream.getUpgradeRequest().getRequestURI().getRawQuery());
            this.manager.add(query.get("clientId").getAsString(), new SessionModel(stream));
            System.out.printf(
                "Client connection accepted at '%s'%n",
                stream.getRemoteAddress()
            );
        } catch (Exception e) {
            System.out.printf(
                "Client connection rejected at '%s'%n",
                stream.getRemoteAddress()
            );
        }
    }

    @OnWebSocketClose
    public void onClose(final Session stream, final int statusCode, final String reason) {
        final var query = RequestUtils.getQuery(stream.getUpgradeRequest().getRequestURI().getRawQuery());
        this.manager.remove(query.get("clientId").getAsString());

        System.out.printf(
            "Client disconnection at '%s' with code: '%s' reason: '%s'%n",
            stream.getRemoteAddress(), statusCode, reason
        );
    }

    @OnWebSocketMessage
    public void onMessage(final Session stream, final String message) {
        System.out.printf(
            "Client message at '%s'%n",
            stream.getRemoteAddress()
        );

        final var query = RequestUtils.getQuery(stream.getUpgradeRequest().getRequestURI().getRawQuery());

        try {
            final var payload = ConstantUtils.GSON.fromJson(message, SocketPayload.class);
            final var fileId = payload.getFileId();
            final var request = payload.getRequest();

            switch (request) {
                case "listen" -> {
                    this.manager.get(query.get("clientId").getAsString()).setFileId(fileId);
                    
                    this.manager.send(stream, request, "Currently listening to '%s'.".formatted(fileId));
                }
                case "list" -> {
                    final var files = FileManager.getFiles();
                    final List<String> names = files.stream().map(File::getName).toList();

                    final var jsonObject = new JsonObject();
                    jsonObject.add("files", ConstantUtils.GSON.toJsonTree(names));

                    this.manager.send(stream, request, jsonObject);
                }
                case "create.single" -> {
                    final var content = payload.getContent();

                    final var log = new DefaultLogModel(content.get("log").getAsJsonObject());
                    FileManager.writeLine(fileId, log.getAsLine());

                    this.manager.send(stream, request, "Successfully written log in specified file.");
                }
                case "create.multiple" -> {
                    final var content = payload.getContent();

                    final var entries = content.get("logs").getAsJsonArray().asList();
                    final var logs = entries.stream().map(entry -> new DefaultLogModel(entry.getAsJsonObject())).toList();

                    FileManager.writeLines(fileId, logs.stream().map(LogModel::getAsLine).toList());

                    this.manager.send(stream, request, "Successfully written logs in specified file.");
                }
                case "fetch.matching" -> {

                }
                case "fetch.all" -> {
                    final var lines = FileManager.readLines(fileId);
                    final var logs = lines.stream().map(DefaultLogModel::new).toList();

                    final var jsonObject = new JsonObject();
                    jsonObject.addProperty("size", logs.size());
                    jsonObject.add("logs", ConstantUtils.GSON.toJsonTree(logs));

                    this.manager.send(stream, "fetch.all", jsonObject);
                }
            }
        } catch (Exception e) {
            this.manager.disconnect(query.get("clientId").getAsString(), 500, "Payload is missing/format was not recognized.", e.getMessage());
        }
    }
}
