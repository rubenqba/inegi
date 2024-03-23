// Observation.java

package com.rubenqba.inegi.api.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Observation(
        @JsonProperty("TIME_PERIOD") String period,
        @JsonProperty("OBS_VALUE") String value,
        @JsonProperty("OBS_EXCEPTION") String exception,
        @JsonProperty("OBS_STATUS") String status,
        @JsonProperty("OBS_SOURCE") String source,
        @JsonProperty("OBS_NOTE") String note,
        @JsonProperty("COBER_GEO") String coverage
) {
}