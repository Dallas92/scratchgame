package com.cyberspeed.utils;

import com.cyberspeed.models.config.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MatrixFormUtils {
    private static final Random randomizer = new Random();

    public static String[][] formMatrixWithStandardSymbols(GameConfig config) {
        String[][] matrix = new String[config.getRows()][config.getColumns()];

        for (int r = 0; r < config.getRows(); r++) {
            for (int c = 0; c < config.getColumns(); c++) {
                matrix[r][c] = MatrixSymbolUtils.getStandardSymbol(config, r, c);
            }
        }
        return matrix;
    }

    public static String putBonusSymbolToMatrix(String[][] matrix, GameConfig config) {
        String bonusSymbol = MatrixSymbolUtils.getBonusSymbol(config);
        int row = randomizer.nextInt(0, matrix.length);
        int col = randomizer.nextInt(0, matrix[0].length);
        matrix[row][col] = bonusSymbol;
        return bonusSymbol;
    }
}
