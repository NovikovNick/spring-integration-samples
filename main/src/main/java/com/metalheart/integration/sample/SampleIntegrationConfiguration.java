package com.metalheart.integration.sample;

import com.metalheart.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
@ComponentScan("com.metalheart.integration.sample")
@IntegrationComponentScan("com.metalheart.integration.sample")
public class SampleIntegrationConfiguration {

    @Bean
    public IntegrationFlow happyFlow(TestHandler handler) {

        return IntegrationFlows
                .from(Constant.SAMPLE_CHANNEL)
                .handle(handler)
                .get();
    }

    @Bean
    public IntegrationFlow errorFlow() {

        return IntegrationFlows
                .from(Constant.SAMPLE_ERROR_CHANNEL)
                .handle((payload, headers) -> {
                    System.out.println("ERROR!");
                    return false;
                })
                .get();
    }
}
