package com.cyberspeed.utils;

import com.cyberspeed.Application;
import com.cyberspeed.models.config.GameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class ConfigUtils {
    public static GameConfig readConfig(String configFile) throws IOException {
        try (InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(configFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Config File '%s' not found!".formatted(configFile));
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return objectMapper.readValue(inputStream, GameConfig.class);
        }
    }
}
