package com.rubenqba.inegi.config;

import com.rubenqba.inegi.api.InegiService;
import com.rubenqba.inegi.api.impl.InegiServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * GlobalConfiguration summary here...
 *
 * @author rbresler
 **/
@Configuration
public class GlobalConfiguration {

    @Bean
    @ConditionalOnMissingBean
    InegiService createInegiService(@Value("${inegi.token}") String token) {
        return new InegiServiceImpl(RestClient.builder(), token);
    }
}
