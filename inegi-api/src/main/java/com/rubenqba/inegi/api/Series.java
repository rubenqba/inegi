package com.rubenqba.inegi.api;

import java.time.temporal.Temporal;

/**
 * Series summary here...
 *
 * @author rbresler
 **/
public record Series<T extends Temporal, V>(T period, V value) {
}
