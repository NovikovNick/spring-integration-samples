package com.metalheart.showcase.receiver;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.ReceiverMQConfiguration;
import com.metalheart.showcase.common.SenderMQConfiguration;
import com.metalheart.showcase.common.model.RequestEvent;
import com.metalheart.showcase.common.transformer.EventToJsonTransformer;
import com.metalheart.showcase.common.transformer.MessageToEventTransformer;
import com.metalheart.showcase.receiver.service.ReceiverService;
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
public class ReceiverIntegrationConfiguration {

    @Bean
    public IntegrationFlow receiverInboundMQ(ConnectionFactory connectionFactory,
                                             @Qualifier(SenderMQConfiguration.TEMPLATE_NAME) AmqpTemplate amqpTemplate,
                                             MessageToEventTransformer messageToEvent,
                                             ReceiverService service,
                                             EventToJsonTransformer eventToJson) {


        return IntegrationFlows
            .from(Amqp.inboundAdapter(connectionFactory, ReceiverMQConfiguration.QUEUE_NAME))
            .wireTap(f -> f.handle(msg -> log.info("receiver get: " + msg.getPayload())))
            .transform(messageToEvent)
            .<RequestEvent>handle((payload, headers) -> service.calculate(payload))
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
