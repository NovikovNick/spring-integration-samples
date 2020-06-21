package com.metalheart.showcase.common.config;

import com.metalheart.showcase.common.Receiver2MQConfiguration;
import com.metalheart.showcase.common.ReceiverMQConfiguration;
import com.metalheart.showcase.common.SenderMQConfiguration;
import com.metalheart.showcase.receiver.ReceiverIntegrationConfiguration;
import com.metalheart.showcase.receiver2.Receiver2IntegrationConfiguration;
import com.metalheart.showcase.sender.SenderIntegrationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@Import({
    ReceiverMQConfiguration.class,
    Receiver2MQConfiguration.class,
    SenderMQConfiguration.class,
    ReceiverIntegrationConfiguration.class,
    Receiver2IntegrationConfiguration.class,
    SenderIntegrationConfiguration.class
})
@ComponentScan("com.metalheart.showcase")
@EnableIntegration
@IntegrationComponentScan("com.metalheart.showcase")
public class ShowcaseConfiguration {
}
