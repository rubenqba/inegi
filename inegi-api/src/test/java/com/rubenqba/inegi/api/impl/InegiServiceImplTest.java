package com.rubenqba.inegi.api.impl;

import com.rubenqba.inegi.api.InegiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * InegiServiceImplTest summary here...
 *
 * @author rbresler
 **/
@SpringBootTest
@ActiveProfiles("test")
class InegiServiceImplTest {

    private static String token;
    private InegiService service;

    @BeforeAll
    static void beforeAll(@Value("${inegi.token}") String secret) {
        token = secret;
    }

    @BeforeEach
    void setUp() {
        RestClient.Builder clientBuilder = RestClient.builder();
        service = new InegiServiceImpl(clientBuilder, token);
    }

    @Test
    void lastMonthlyInflationRate() {
        assertThat(service.getLastMonthlyInflationRate()).isPresent()
                .hasValueSatisfying(inflation -> {
                    System.out.println(inflation);
                    assertThat(inflation.value()).isPositive();
                    assertThat(inflation.period()).isNotNull();
                });
    }

    @Test
    void historicalMonthlyInflationRates() {
        assertThat(service.getHistoricalMonthlyInflationRates()).isNotEmpty()
                .allSatisfy(inflation -> {
                    System.out.println(inflation);
                    assertThat(inflation.value()).isPositive();
                    assertThat(inflation.period()).isNotNull();
                });
    }

    @Test
    void lastCumulativeAnnualInflationRate() {
        assertThat(service.getLastCumulativeAnnualInflationRate()).isPresent()
                .hasValueSatisfying(inflation -> {
                    System.out.println(inflation);
                    assertThat(inflation.value()).isNotNull();
                    assertThat(inflation.period()).isNotNull();
                });
    }

    @Test
    void historicalCumulativeAnnualInflationRates() {
        assertThat(service.getHistoricalCumulativeAnnualInflationRates()).isNotEmpty()
                .allSatisfy(inflation -> {
                    System.out.println(inflation);
                    assertThat(inflation.value()).isNotNull();
                    assertThat(inflation.period()).isNotNull();
                });
    }

    @Test
    void lastConsumerPriceIndex() {
        assertThat(service.getLastConsumerPriceIndex()).isPresent()
                .hasValueSatisfying(index -> {
                    System.out.println(index);
                    assertThat(index.value()).isPositive();
                    assertThat(index.period()).isNotNull();

                    assertThat(service.getConsumerPriceIndex(index.period())).isPresent()
                            .hasValueSatisfying(periodIndex -> assertThat(periodIndex).usingRecursiveComparison().isEqualTo(index));
                });
    }

    @Test
    void historicalConsumerPriceIndex() {
        assertThat(service.getHistoricalConsumerPriceIndex()).isNotEmpty()
                .allSatisfy(index -> {
                    System.out.println(index);
                    assertThat(index.value()).isPositive();
                    assertThat(index.period()).isNotNull();
                });
    }

}