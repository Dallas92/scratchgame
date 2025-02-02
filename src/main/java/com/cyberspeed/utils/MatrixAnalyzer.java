package com.cyberspeed.utils;

import com.cyberspeed.models.config.GameConfig;
import com.cyberspeed.models.config.combination.CoveredAreasWinCombination;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@UtilityClass
public class MatrixAnalyzer {

    private static final String DELIMITER = ":";

    public static Map<String, TreeSet<String>> getWinSymbols(String[][] matrix, GameConfig config) {
        Map<String, Integer> symbolsCount = new HashMap<>();
        for (int r = 0; r < config.getRows(); r++) {
            for (int c = 0; c < config.getColumns(); c++) {
                String symbol = matrix[r][c];

                if (symbolsCount.putIfAbsent(symbol, 1) != null) {
                    symbolsCount.put(symbol, symbolsCount.get(symbol) + 1);
                }
            }
        }

        Map<String, TreeSet<String>> winSymbols = new HashMap<>();
        for (var wc : config.getWinCombinations().entrySet()) {
            switch (wc.getValue().getGroup()) {
                case same_symbols:
                    for (var s : symbolsCount.entrySet()) {
                        if (wc.getKey().equals("same_symbol_%s_times".formatted(s.getValue()))) {
                            TreeSet<String> set = Optional.ofNullable(winSymbols.get(s.getKey())).orElse(new TreeSet<>());
                            set.add(wc.getKey());
                            winSymbols.put(s.getKey(), set);
                        }
                    }
                    break;
                case horizontally_linear_symbols:
                case vertically_linear_symbols:
                case ltr_diagonally_linear_symbols:
                case rtl_diagonally_linear_symbols:
                    CoveredAreasWinCombination cwc = (CoveredAreasWinCombination) wc.getValue();
                    for (var winCoordinates : cwc.getCoveredAreas()) {
                        Set<String> symbols = new TreeSet<>();
                        for (var coordinate : winCoordinates) {
                            String[] coordinates = coordinate.split(DELIMITER);
                            symbols.add(matrix[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]);
                        }

                        if (symbols.size() == 1) {
                            String winningSymbol = symbols.stream().findFirst().orElse(null);
                            TreeSet<String> set = Optional.ofNullable(winSymbols.get(winningSymbol)).orElse(new TreeSet<>());
                            set.add(wc.getKey());
                            winSymbols.put(winningSymbol, set);
                        }
                    }
                    break;
            }
        }

        return winSymbols;
    }
}
