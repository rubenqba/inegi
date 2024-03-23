package com.rubenqba.inegi.api.impl;

import com.rubenqba.inegi.api.InegiService;
import com.rubenqba.inegi.api.Series;
import com.rubenqba.inegi.api.impl.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * InegiServiceImpl summary here...
 *
 * @author rbresler
 **/
public class InegiServiceImpl implements InegiService {
    private static final Logger log = LoggerFactory.getLogger(InegiServiceImpl.class);

    private static final String DATA_URL = "https://www.inegi.org.mx/app/api/indicadores/desarrolladores/jsonxml/INDICATOR/{indicator}/es/{area}/{lastValue}/{sources}/2.0/{token}?type=json";

    private final RestClient client;
    private final String token;

    public InegiServiceImpl(RestClient.Builder clientBuilder, String token) {
        this.client = clientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build();
        this.token = token;
    }


    Response getData(String indicator, String area, boolean lastValue, String sources, String token) {
        log.trace("getData: indicator={}, area={}, lastValue={}, sources={}", indicator, area, lastValue, sources);
        return client
                .get()
                .uri(DATA_URL, indicator, area, lastValue, sources, token)
                .retrieve()
                .body(Response.class);
    }

    private Optional<List<com.rubenqba.inegi.api.impl.model.Series>> getIndicators(String indicator, String area, boolean lastValue, String sources) {
        final var response = getData(indicator, area, lastValue, sources, token);
        log.trace("response: {}", response);
        if (response == null || CollectionUtils.isEmpty(response.series())) {
            return Optional.empty();
        }
        return Optional.of(response.series());
    }

    private Series<YearMonth, BigDecimal> observationToBigDecimalSeries(Observation observation) {
        if (StringUtils.hasText(observation.exception())) {
            log.warn("observation with exception: {}", observation);
            return null;
        }
        YearMonth period = YearMonth.parse(observation.period(), DateTimeFormatter.ofPattern("uuuu/MM"));
        return new Series<>(period, new BigDecimal(observation.value()));
    }

    @Override
    public Optional<Series<YearMonth, BigDecimal>> getLastMonthlyInflationRate() {
        log.debug("requesting last monthly inflation rate value");
        return getIndicators(Indicators.INFLATION_RATE, GeographicAreas.NATIONAL, true, DataSource.BIE)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(List::getFirst)
                .filter(series -> !CollectionUtils.isEmpty(series.observations()))
                .map(series -> series.observations().getFirst())
                .map(this::observationToBigDecimalSeries);
    }

    @Override
    public List<Series<YearMonth, BigDecimal>> getHistoricalMonthlyInflationRates() {
        log.debug("requesting historical monthly inflation rate values");
        return getIndicators(Indicators.INFLATION_RATE, GeographicAreas.NATIONAL, false, DataSource.BIE)
                .map(list -> list.stream()
                        .flatMap(series -> series.observations().stream())
                        .map(this::observationToBigDecimalSeries)
                        .filter(Objects::nonNull))
                .orElseGet(Stream::empty)
                .toList();
    }

    @Override
    public Optional<Series<YearMonth, BigDecimal>> getLastCumulativeAnnualInflationRate() {
        log.debug("requesting last cumulative annual inflation rate value");
        return getIndicators(Indicators.INFLATION_RATE_CUMULATIVE, GeographicAreas.NATIONAL, true, DataSource.BIE)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(List::getFirst)
                .filter(series -> !CollectionUtils.isEmpty(series.observations()))
                .map(series -> series.observations().getFirst())
                .map(this::observationToBigDecimalSeries);
    }

    @Override
    public List<Series<YearMonth, BigDecimal>> getHistoricalCumulativeAnnualInflationRates() {
        return getIndicators(Indicators.INFLATION_RATE_CUMULATIVE, GeographicAreas.NATIONAL, false, DataSource.BIE)
                .map(list -> list.stream()
                        .flatMap(series -> series.observations().stream())
                        .map(this::observationToBigDecimalSeries)
                        .filter(Objects::nonNull))
                .orElseGet(Stream::empty)
                .toList();
    }

    @Override
    public Optional<Series<YearMonth, BigDecimal>> getLastConsumerPriceIndex() {
        log.debug("requesting last IPC value");
        return getIndicators(Indicators.CONSUMER_PRICE_INDEX, GeographicAreas.NATIONAL, true, DataSource.BIE)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(List::getFirst)
                .filter(series -> !CollectionUtils.isEmpty(series.observations()))
                .map(series -> series.observations().getFirst())
                .map(this::observationToBigDecimalSeries);
    }

    @Override
    public Optional<Series<YearMonth, BigDecimal>> getConsumerPriceIndex(YearMonth period) {
        return getHistoricalConsumerPriceIndex().stream().filter(series -> series.period().equals(period)).findFirst();
    }

    @Override
    public List<Series<YearMonth, BigDecimal>> getHistoricalConsumerPriceIndex() {
        log.debug("requesting historical IPC values");
        return getIndicators(Indicators.CONSUMER_PRICE_INDEX, GeographicAreas.NATIONAL, false, DataSource.BIE)
                .map(list -> list.stream()
                        .flatMap(series -> series.observations().stream())
                        .map(this::observationToBigDecimalSeries))
                .orElseGet(Stream::empty)
                .toList();
    }
}
