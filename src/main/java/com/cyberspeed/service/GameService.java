package com.cyberspeed.service;

import com.cyberspeed.models.GameResult;
import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.utils.MatrixAnalyzer;
import com.cyberspeed.utils.MatrixFormUtils;
import com.cyberspeed.utils.MatrixRewardUtils;

import java.util.Map;
import java.util.TreeSet;

public class GameService {

    private static final String IGNORE_BONUS_SYMBOL = "MISS";
    private final Integer bettingAmount;
    private final GameConfig config;

    public GameService(Integer bettingAmount, GameConfig config) {
        this.bettingAmount = bettingAmount;
        this.config = config;
    }

    public GameResult play() {
        String[][] matrix = MatrixFormUtils.formMatrixWithStandardSymbols(config);

        String appliedBonusSymbol = MatrixFormUtils.putBonusSymbolToMatrix(matrix, config);
        Map<String, TreeSet<String>> appliedWinningCombinations = MatrixAnalyzer.getWinSymbols(matrix, config);

        return formResult(this.bettingAmount, this.config, appliedWinningCombinations, matrix, appliedBonusSymbol);
    }

    private GameResult formResult(Integer bettingAmount, GameConfig config, Map<String, TreeSet<String>> appliedWinningCombinations, String[][] matrix, String appliedBonusSymbol) {
        if (appliedWinningCombinations.isEmpty()) {
            return new GameResult(
                    matrix,
                    0,
                    null,
                    null
            );
        } else {
            double reward = MatrixRewardUtils.calculateReward(bettingAmount, appliedWinningCombinations, appliedBonusSymbol, config);
            return new GameResult(
                    matrix,
                    reward,
                    appliedWinningCombinations,
                    appliedBonusSymbol.equals(IGNORE_BONUS_SYMBOL) ? null : appliedBonusSymbol
            );
        }
    }
}
