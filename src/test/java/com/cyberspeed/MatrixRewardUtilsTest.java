package com.cyberspeed;

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
import com.cyberspeed.utils.MatrixRewardUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixRewardUtilsTest {

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    void testCalculateReward(double bettingAmount, Map<String, TreeSet<String>> appliedWinningCombinations, String appliedBonusSymbol, GameConfig config, double expectedReward) {
        double reward = MatrixRewardUtils.calculateReward(bettingAmount, appliedWinningCombinations, appliedBonusSymbol, config);
        assertEquals(expectedReward, reward);
    }

    static Stream<Arguments> provideTestArguments() {
        GameConfig config = new GameConfig();
        config.setRows(3);
        config.setColumns(3);
        config.setSymbols(
                Map.of(
                        "A", new StandardSymbol(5),
                        "B", new StandardSymbol(3),
                        "+1000", new BonusSymbol(null, BonusImpactEnum.extra_bonus, 1000),
                        "5x", new BonusSymbol(5D, BonusImpactEnum.multiply_reward, null),
                        "10x", new BonusSymbol(10D, BonusImpactEnum.multiply_reward, null),
                        "MISS", new BonusSymbol(null, BonusImpactEnum.miss, null)
                )
        );
        config.setProbabilities(new Probabilities(
                List.of(
                        new StandardSymbolProbability(0, 0, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(0, 1, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(1, 0, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(1, 1, Map.of("A", 1, "B", 2))
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
                "same_symbols_horizontally", new CoveredAreasWinCombination(new String[0][0], 2, WinCombinationGroupEnum.horizontally_linear_symbols)
        ));

        return Stream.of(
                Arguments.arguments(100D, Map.of(), null, config, 0),
                Arguments.arguments(100D, Map.of("A", new TreeSet<>(Set.of("same_symbol_4_times"))), "MISS", config, 750),
                Arguments.arguments(100D, Map.of("A", new TreeSet<>(Set.of("same_symbol_4_times", "same_symbols_horizontally"))), "MISS", config, 1500),
                Arguments.arguments(100D, Map.of("A", new TreeSet<>(Set.of("same_symbol_4_times", "same_symbols_horizontally"))), "+1000", config, 2500),
                Arguments.arguments(100D, Map.of("A", new TreeSet<>(Set.of("same_symbol_4_times", "same_symbols_horizontally"))), "5x", config, 7500),
                Arguments.arguments(100D, Map.of("B", new TreeSet<>(Set.of("same_symbol_3_times"))), "10x", config, 3000)
        );
    }
}
