package com.rubenqba.inegi.ctrl.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * ConsumerPriceIndexRate summary here...
 *
 * @author rbresler
 **/
public record ConsumerPriceIndexRate(YearMonth initialPeriod, YearMonth finalPeriod, BigDecimal value) {
}
