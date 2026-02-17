package com.m7sistemas.xpathworkbench.service;

import com.m7sistemas.xpathworkbench.model.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigService {

    private static final String APP_FOLDER = ".xpathworkbench";
    private static final String CONFIG_FILE = "config.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private Path getConfigPath() throws IOException {

        String userHome = System.getProperty("user.home");

        Path appDirectory = Paths.get(userHome, APP_FOLDER);

        if (!Files.exists(appDirectory)) {
            Files.createDirectories(appDirectory);
        }

        return appDirectory.resolve(CONFIG_FILE);
    }

    public void save(AppConfig config) throws IOException {

        Path configPath = getConfigPath();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(configPath.toFile(), config);
    }

    public AppConfig load() throws IOException {

        Path configPath = getConfigPath();

        if (!Files.exists(configPath)) {
            return new AppConfig("Dark"); // valor padrão
        }

        return mapper.readValue(configPath.toFile(), AppConfig.class);
    }
}