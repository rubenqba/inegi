// Series.java

package com.rubenqba.inegi.api.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Series(
        @JsonProperty("INDICADOR") String indicator,
        @JsonProperty("FREQ") String frequency,
        @JsonProperty("TOPIC") String topic,
        @JsonProperty("UNIT") String unit,
        @JsonProperty("UNIT_MULT") String multiplierUnit,
        @JsonProperty("NOTE") String note,
        @JsonProperty("SOURCE") String source,
        @JsonProperty("LASTUPDATE") String lastUpdate,
        @JsonProperty("STATUS") Object status,
        @JsonProperty("OBSERVATIONS") List<Observation> observations
) {
}
