package com.cyberspeed.models.config.combination;

import com.cyberspeed.models.config.enums.WinCombinationGroupEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoveredAreasWinCombination extends WinCombination {
    private String[][] coveredAreas;

    public CoveredAreasWinCombination(String[][] coveredAreas, double rewardMultiplier, WinCombinationGroupEnum group) {
        super(rewardMultiplier, group);

        this.coveredAreas = coveredAreas;
    }
}
