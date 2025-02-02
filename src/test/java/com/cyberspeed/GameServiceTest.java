package com.cyberspeed;

import com.cyberspeed.models.GameResult;
import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.models.config.combination.CountBasedWinCombination;
import com.cyberspeed.models.config.combination.CoveredAreasWinCombination;
import com.cyberspeed.models.config.enums.BonusImpactEnum;
import com.cyberspeed.models.config.enums.WinCombinationGroupEnum;
import com.cyberspeed.models.config.probability.BonusSymbols;
import com.cyberspeed.models.config.probability.Probabilities;
import com.cyberspeed.models.config.probability.StandardSymbolProbability;
import com.cyberspeed.models.config.symbol.BonusSymbol;
import com.cyberspeed.models.config.symbol.StandardSymbol;
import com.cyberspeed.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {

    @Test
    void testGameService() {
        GameConfig config = new GameConfig();
        config.setRows(3);
        config.setColumns(3);
        config.setSymbols(
                Map.of(
                        "A", new StandardSymbol(5),
                        "B", new StandardSymbol(3),
                        "C", new StandardSymbol(3),
                        "D", new StandardSymbol(3),
                        "E", new StandardSymbol(3),
                        "F", new StandardSymbol(3),
                        "+1000", new BonusSymbol(null, BonusImpactEnum.extra_bonus, 1000),
                        "5x", new BonusSymbol(5D, BonusImpactEnum.multiply_reward, null),
                        "10x", new BonusSymbol(10D, BonusImpactEnum.multiply_reward, null),
                        "MISS", new BonusSymbol(null, BonusImpactEnum.miss, null)
                )
        );
        config.setProbabilities(new Probabilities(
                List.of(
                        new StandardSymbolProbability(0, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(0, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(0, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(1, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(1, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(1, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(2, 0, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(2, 1, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6)),
                        new StandardSymbolProbability(2, 2, Map.of("A", 1, "B", 2, "C", 3, "D", 4, "E", 5, "F", 6))
                ),
                new BonusSymbols(Map.of(
                        "+1000", 1,
                        "5x", 2,
                        "10x", 3,
                        "MISS", 4))
        ));
        config.setWinCombinations(Map.of(
                "same_symbol_3_times", new CountBasedWinCombination(1, 1, WinCombinationGroupEnum.same_symbols),
                "same_symbol_4_times", new CountBasedWinCombination(1, 1.5, WinCombinationGroupEnum.same_symbols),
                "same_symbol_5_times", new CountBasedWinCombination(1, 1.5, WinCombinationGroupEnum.same_symbols),
                "same_symbol_6_times", new CountBasedWinCombination(1, 1.5, WinCombinationGroupEnum.same_symbols),
                "same_symbol_7_times", new CountBasedWinCombination(1, 1.5, WinCombinationGroupEnum.same_symbols),
                "same_symbol_8_times", new CountBasedWinCombination(1, 1.5, WinCombinationGroupEnum.same_symbols),
                "same_symbols_horizontally", new CoveredAreasWinCombination(
                        new String[][]{
                                new String[]{"0:0", "0:1", "0:2"},
                                new String[]{"1:0", "1:1", "1:2"},
                                new String[]{"2:0", "2:1", "2:2"}
                        }, 2, WinCombinationGroupEnum.horizontally_linear_symbols
                ),
                "same_symbols_vertically", new CoveredAreasWinCombination(
                        new String[][]{
                                new String[]{"0:0", "1:0", "2:0"},
                                new String[]{"0:1", "1:1", "2:1"},
                                new String[]{"0:2", "1:2", "2:2"}
                        }, 2, WinCombinationGroupEnum.horizontally_linear_symbols
                )
        ));

        GameService gameService = new GameService(100, config);
        GameResult gameResult = gameService.play();

        assertNotNull(gameResult);
        assertNotEquals(0, gameResult.getMatrix().length);
        assertNotEquals(0, gameResult.getMatrix()[0].length);
        assertTrue(gameResult.getReward() >= 0);

        if (gameResult.getReward() > 0) {
            assertFalse(gameResult.getAppliedWinningCombinations().isEmpty());
        }
    }
}
