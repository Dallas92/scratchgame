package com.cyberspeed;

import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.service.GameService;
import com.cyberspeed.utils.CmdArgsUtils;
import com.cyberspeed.utils.ConfigUtils;

import java.io.IOException;
import java.util.Map;

public class Application {

    public static void main(String[] args) throws IOException {
        Map<String, String> params = CmdArgsUtils.parseArgs(args);
        String configFile = params.get("--config");
        Integer bettingAmount = Integer.parseInt(params.get("--betting-amount"));
        GameConfig config = ConfigUtils.readConfig(configFile);

        GameService gameService = new GameService(bettingAmount, config);
        System.out.println(gameService.play());
    }
}
