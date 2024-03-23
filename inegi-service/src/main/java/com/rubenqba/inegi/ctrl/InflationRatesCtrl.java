package com.rubenqba.inegi.ctrl;

import com.rubenqba.inegi.api.InegiService;
import com.rubenqba.inegi.ctrl.dto.ConsumerPriceIndexRate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

/**
 * InflationRatesCtrl summary here...
 *
 * @author rbresler
 **/
@RestController
public class InflationRatesCtrl {

    private final InegiService service;

    public InflationRatesCtrl(InegiService service) {
        this.service = service;
    }

    @GetMapping("/inflation/{first}/{last}")
    public ConsumerPriceIndexRate computeInflationRateBetweenDates(@PathVariable YearMonth first, @PathVariable YearMonth last) {

        var lastPeriod = service.getLastConsumerPriceIndex()
                .map(s -> (YearMonth) s.period())
                .orElseThrow(() -> new RuntimeException("There are no IPC rates available"));
        final var end = lastPeriod.isAfter(last) ? last : lastPeriod;

        if (first.equals(end)) {
            return new ConsumerPriceIndexRate(first, end, BigDecimal.ZERO);
        }

        var startRate = service.getConsumerPriceIndex(first).orElseThrow(() -> new RuntimeException(String.format("There are no IPC rates available for '%s'", first)));
        var endRate = service.getConsumerPriceIndex(end).orElseThrow(() -> new RuntimeException(String.format("There are no IPC rates available for '%s'", end)));

        var rate = endRate.value().divide(startRate.value(), RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
        rate = rate.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);

        return new ConsumerPriceIndexRate(first, end, rate);
    }

}
