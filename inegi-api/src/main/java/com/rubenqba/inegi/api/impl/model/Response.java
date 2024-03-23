package com.rubenqba.inegi.api.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Response(
        @JsonProperty("Header") Header header,
        @JsonProperty("Series") List<Series> series) {

}




