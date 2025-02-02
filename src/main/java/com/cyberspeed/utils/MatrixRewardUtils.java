package com.cyberspeed.utils;

import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.models.config.symbol.BonusSymbol;
import com.cyberspeed.models.config.symbol.StandardSymbol;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.TreeSet;

@UtilityClass
public class MatrixRewardUtils {
    public static double calculateReward(double bettingAmount, Map<String, TreeSet<String>> appliedWinningCombinations, String appliedBonusSymbol, GameConfig config) {
        double reward = 0;

        if (appliedWinningCombinations.isEmpty()) {
            return reward;
        }

        for (var symbol : appliedWinningCombinations.entrySet()) {
            StandardSymbol standardSymbol = (StandardSymbol) config.getSymbols().get(symbol.getKey());
            double symbolReward = bettingAmount * standardSymbol.getRewardMultiplier();
            for (var combination : symbol.getValue()) {
                symbolReward *= config.getWinCombinations().get(combination).getRewardMultiplier();
            }

            reward += symbolReward;
        }

        BonusSymbol bonusSymbol = (BonusSymbol) config.getSymbols().get(appliedBonusSymbol);
        switch (bonusSymbol.getImpact()) {
            case multiply_reward:
                reward *= bonusSymbol.getRewardMultiplier();
                break;
            case extra_bonus:
                reward += bonusSymbol.getExtra();
                break;
            case miss:
                break;
        }

        return reward;
    }
}
