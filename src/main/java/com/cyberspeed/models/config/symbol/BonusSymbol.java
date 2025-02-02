package com.cyberspeed.models.config.symbol;

import com.cyberspeed.models.config.enums.BonusImpactEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusSymbol extends Symbol {
    private Double rewardMultiplier;
    private BonusImpactEnum impact;
    private Integer extra;
}
