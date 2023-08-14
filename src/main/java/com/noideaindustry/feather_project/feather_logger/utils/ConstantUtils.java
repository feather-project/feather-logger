package com.noideaindustry.feather_project.feather_logger.utils;

import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConstantUtils {
    public static final Gson GSON = new Gson();
    public static final Executor ASYNC = Executors.newCachedThreadPool();
}
