package com.cyberspeed.models.config.combination;

import com.cyberspeed.models.config.enums.WinCombinationGroupEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountBasedWinCombination extends WinCombination {
    private int count;

    public CountBasedWinCombination(int count, double rewardMultiplier, WinCombinationGroupEnum group) {
        super(rewardMultiplier, group);

        this.count = count;
    }
}
