package com.noideaindustry.feather_project.feather_logger.controllers;

import com.noideaindustry.feather_project.feather_logger.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/sse/v1/logger")
public class ReadLogs {
    @GetMapping("/logs")
    public SseEmitter readLogs(@RequestParam(name = "id") final String id) {
        final var emitter = new SseEmitter(Long.MAX_VALUE);
        Logger.get().getSessionManager().addSession(id, emitter);

        emitter.onCompletion(() -> {
            Logger.get().getSessionManager().removeSession(id);
        });

        try { emitter.send(SseEmitter.event().comment("Successfully connected.")); }
        catch (IOException e) { emitter.completeWithError(e); }

        return emitter;
    }
}
