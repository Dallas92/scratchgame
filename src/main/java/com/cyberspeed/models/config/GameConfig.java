package com.cyberspeed.models.config;

import com.cyberspeed.models.config.combination.WinCombination;
import com.cyberspeed.models.config.probability.Probabilities;
import com.cyberspeed.models.config.symbol.Symbol;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GameConfig {
    private Integer rows = 3;
    private Integer columns = 3;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    private Map<String, WinCombination> winCombinations;
}
