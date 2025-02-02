package com.cyberspeed.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class GameResult {
    private String[][] matrix;
    private double reward;
    private Map<String, TreeSet<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");

        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("</br> ");
        sb.append("\"matrix\": [ ");
        sb.append("</br> ");

        for (int i = 0; i < matrix.length; i++) {
            sb.append("[");
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append("\"").append(matrix[i][j]).append("\"");
                if (j != matrix[0].length - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");

            if (i != matrix.length - 1) {
                sb.append(",");
            }
            sb.append(" </br> ");
        }

        sb.append("], ");
        sb.append("</br> ");
        sb.append("\"reward\": ");
        sb.append(df.format(reward));

        if (reward > 0) {
            sb.append(", </br> ");

            sb.append("\"applied_winning_combinations\": ");
            sb.append("{");
            sb.append("</br> ");

            var iterator = appliedWinningCombinations.entrySet().iterator();
            while (iterator.hasNext()) {
                var symbolCombs = iterator.next();
                var isLast = !iterator.hasNext();

                sb.append("\"").append(symbolCombs.getKey()).append("\"");
                sb.append(": ");
                sb.append("[");
                sb.append(symbolCombs.getValue().stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")));
                sb.append("]");
                if (!isLast) {
                    sb.append(",");
                }
                sb.append(" </br> ");
            }

            sb.append("}");

            if (appliedBonusSymbol != null) {
                sb.append(", </br> ");
                sb.append("\"applied_bonus_symbol\": ");
                sb.append("\"").append(appliedBonusSymbol).append("\"");
            }
        } else {
            sb.append(" </br> ");
        }

        sb.append("}");

        return sb.toString();
    }
}
