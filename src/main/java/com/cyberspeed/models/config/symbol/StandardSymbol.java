package com.cyberspeed.models.config.symbol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardSymbol extends Symbol {
    private double rewardMultiplier;
}
