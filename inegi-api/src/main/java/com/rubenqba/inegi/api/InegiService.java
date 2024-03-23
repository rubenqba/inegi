package com.rubenqba.inegi.api;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de integración con los datos del INEGI
 *
 * @author rbresler
 **/
public interface InegiService {

    /**
     * Devuelve la tasa de inflación mensual del último mes
     *
     * @return tasa de inflación del último mes
     */
    Optional<Series<YearMonth, BigDecimal>> getLastMonthlyInflationRate();

    /**
     * Devuelve la tasa de inflación mensual histórica desde Enero de 1970 hasta la fecha actual
     *
     * @return Series con la tasa de inflación mensual histórica
     */
    List<Series<YearMonth, BigDecimal>> getHistoricalMonthlyInflationRates();

    /**
     * Devuelve la tasa de inflación acumulada anual
     *
     * @return tasa de inflación
     */
    Optional<Series<YearMonth, BigDecimal>> getLastCumulativeAnnualInflationRate();

    /**
     * Devuelve la tasa de inflación mensual histórica desde Enero de 1970 hasta la fecha actual
     *
     * @return Series con la tasa de inflación mensual histórica
     */
    List<Series<YearMonth, BigDecimal>> getHistoricalCumulativeAnnualInflationRates();

    Optional<Series<YearMonth, BigDecimal>> getLastConsumerPriceIndex();

    Optional<Series<YearMonth, BigDecimal>> getConsumerPriceIndex(YearMonth period);

    /**
     * Devuelve índice histórico de precios al consumidor Enero de 1970
     *
     * @return Series con datos del IPC histórico
     */
    List<Series<YearMonth, BigDecimal>> getHistoricalConsumerPriceIndex();

}
