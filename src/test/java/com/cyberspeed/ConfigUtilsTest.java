package com.cyberspeed;

import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.utils.ConfigUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigUtilsTest {
    @Test
    @SneakyThrows
    void testConfigLoaded() {
        GameConfig config = ConfigUtils.readConfig("config.json");
        assertNotNull(config);
        assertNotNull(config.getRows());
        assertNotNull(config.getColumns());
        assertNotNull(config.getSymbols());
        assertNotNull(config.getProbabilities());
        assertNotNull(config.getWinCombinations());
    }

    @Test
    void testConfigFileNotFound() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ConfigUtils.readConfig("non-existent-config.json")
        );

        assertEquals("Config File 'non-existent-config.json' not found!", exception.getMessage());
    }
}
