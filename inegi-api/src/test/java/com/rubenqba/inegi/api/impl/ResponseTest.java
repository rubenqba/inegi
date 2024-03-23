package com.rubenqba.inegi.api.impl;

import com.rubenqba.inegi.api.impl.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rbresler
 **/
@JsonTest
@ActiveProfiles("test")
class ResponseTest {

    @SpringBootConfiguration
    static class Configuration {
    }

    @Autowired
    private JacksonTester<Response> jacksonTester;


    @Value("classpath:sample.json")
    private Resource data;

    @BeforeEach
    void setUp() {
        assertThat(jacksonTester).isNotNull();

        assertThat(data).isNotNull();
        assertThat(data.exists()).isTrue();
    }

    @Test
    void deserialize() throws IOException {
        Response response = jacksonTester.parseObject(data.getContentAsString(StandardCharsets.UTF_8));
        assertThat(response).isNotNull();

        assertThat(response.header()).isNotNull();
        assertThat(response.series()).isNotEmpty();
    }
}