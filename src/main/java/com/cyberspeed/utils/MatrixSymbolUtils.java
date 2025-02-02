package com.cyberspeed.utils;

import com.cyberspeed.models.config.GameConfig;
import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.stream.IntStream;

@UtilityClass
public class MatrixSymbolUtils {
    private static final Random randomizer = new Random();

    public static String getStandardSymbol(GameConfig config, int row, int col) {
        var probability = config.getProbabilities().getStandardSymbols()
                .stream()
                .filter(ss -> ss.getRow() == row && ss.getColumn() == col)
                .findFirst()
                .orElse(null);

        return probability.getSymbols().entrySet()
                .stream()
                .flatMap(entry -> IntStream.range(0, entry.getValue()).mapToObj(i -> entry.getKey()))
                .toList()
                .get(randomizer.nextInt(probability.getSymbols().values()
                        .stream()
                        .mapToInt(i -> i)
                        .sum()));

    }

    public static String getBonusSymbol(GameConfig config) {
        return config.getProbabilities().getBonusSymbols().getSymbols().entrySet()
                .stream()
                .flatMap(entry -> IntStream.range(0, entry.getValue()).mapToObj(i -> entry.getKey()))
                .toList()
                .get(randomizer.nextInt(config.getProbabilities().getBonusSymbols().getSymbols().values()
                        .stream()
                        .mapToInt(i -> i)
                        .sum()));
    }
}
