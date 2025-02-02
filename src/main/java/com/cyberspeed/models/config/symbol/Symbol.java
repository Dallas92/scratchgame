package com.cyberspeed.models.config.symbol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StandardSymbol.class, name = "standard"),
        @JsonSubTypes.Type(value = BonusSymbol.class, name = "bonus")
})
public abstract class Symbol {
}
