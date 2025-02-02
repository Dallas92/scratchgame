package com.cyberspeed.models.config.combination;

import com.cyberspeed.models.config.enums.WinCombinationGroupEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "when"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoveredAreasWinCombination.class, name = "linear_symbols"),
        @JsonSubTypes.Type(value = CountBasedWinCombination.class, name = "same_symbols")
})
@AllArgsConstructor
@NoArgsConstructor
public abstract class WinCombination {
    private double rewardMultiplier;
    private WinCombinationGroupEnum group;
}
