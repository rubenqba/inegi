// Header.java

package com.rubenqba.inegi.api.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Header(
        @JsonProperty("Name") String name,
        @JsonProperty("Email") String email) {
}