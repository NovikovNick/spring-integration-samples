package com.metalheart.showcase.receiver2;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.Receiver2MQConfiguration;
import com.metalheart.showcase.common.SenderMQConfiguration;
import com.metalheart.showcase.common.model.ResultEvent;
import com.metalheart.showcase.common.transformer.EventToJsonTransformer;
import com.metalheart.showcase.common.transformer.MessageToEventTransformer;
import com.metalheart.showcase.receiver2.service.Receiver2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;

@Slf4j
@Configuration
public class Receiver2IntegrationConfiguration {

    @Bean
    public IntegrationFlow receiver2InboundMQ(ConnectionFactory connectionFactory,
                                             @Qualifier(SenderMQConfiguration.TEMPLATE_NAME) AmqpTemplate amqpTemplate,
                                             MessageToEventTransformer messageToEvent,
                                             Receiver2Service service,
                                             EventToJsonTransformer eventToJson) {


        return IntegrationFlows
            .from(Amqp.inboundAdapter(connectionFactory, Receiver2MQConfiguration.QUEUE_NAME))
            .wireTap(f -> f.handle(msg -> log.info("receiver2 get: " + msg.getPayload())))
            .transform(messageToEvent)
            .<ResultEvent>handle((payload, headers) -> service.calculate(payload))
            .enrichHeaders(h -> h
                .header("contentType", "application/json")
                .headerFunction("amqp_type", this::extractEventType, true)
            )
            .transform(eventToJson)
            .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(SenderMQConfiguration.EXCHANGE_NAME).headersMappedLast(true))
            .get();
    }

    private String extractEventType(Message<SampleEvent> m) {
        return m.getPayload().getType();
    }
}
