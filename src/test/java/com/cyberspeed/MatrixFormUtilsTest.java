package com.cyberspeed;

import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.models.config.enums.BonusImpactEnum;
import com.cyberspeed.models.config.probability.BonusSymbols;
import com.cyberspeed.models.config.probability.Probabilities;
import com.cyberspeed.models.config.probability.StandardSymbolProbability;
import com.cyberspeed.models.config.symbol.BonusSymbol;
import com.cyberspeed.models.config.symbol.StandardSymbol;
import com.cyberspeed.utils.MatrixFormUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatrixFormUtilsTest {

    private static final Set<String> BONUS_SYMBOLS = Set.of("10x", "5x", "+1000", "+500", "MISS");

    private GameConfig config;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        config = new GameConfig();
        config.setRows(2);
        config.setColumns(2);
        config.setSymbols(
                Map.of(
                        "A", new StandardSymbol(1),
                        "B", new StandardSymbol(2),
                        "+1000", new BonusSymbol(null, BonusImpactEnum.extra_bonus, 1000),
                        "5x", new BonusSymbol(5D, BonusImpactEnum.multiply_reward, null)
                )
        );
        config.setProbabilities(new Probabilities(
                List.of(
                        new StandardSymbolProbability(0,0, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(0,1, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(1,0, Map.of("A", 1, "B", 2)),
                        new StandardSymbolProbability(1,1, Map.of("A", 1, "B", 2))
                ),
                new BonusSymbols(Map.of(
                        "+1000", 1,
                        "5x", 2))
        ));
    }

    @Test
    void testFormMatrixWithStandardSymbols() {
        String[][] matrix = MatrixFormUtils.formMatrixWithStandardSymbols(config);

        assertNotNull(matrix);
        assertEquals(2, matrix.length);
        assertEquals(2, matrix[0].length);

        for (String[] row : matrix) {
            for (String cell : row) {
                assertNotNull(cell);
            }
        }
    }

    @Test
    void testPutBonusSymbolToMatrix() {
        String[][] matrix = MatrixFormUtils.formMatrixWithStandardSymbols(config);

        MatrixFormUtils.putBonusSymbolToMatrix(matrix, config);

        int foundBonuses = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (BONUS_SYMBOLS.contains(matrix[i][j])) {
                    foundBonuses++;
                }
            }
        }

        assertEquals(1, foundBonuses);
    }
}
