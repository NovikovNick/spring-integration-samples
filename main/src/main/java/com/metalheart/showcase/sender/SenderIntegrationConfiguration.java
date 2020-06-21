package com.metalheart.showcase.sender;

import com.metalheart.showcase.ShowcaseConstants;
import com.metalheart.showcase.common.Receiver2MQConfiguration;
import com.metalheart.showcase.common.ReceiverMQConfiguration;
import com.metalheart.showcase.common.SenderMQConfiguration;
import com.metalheart.showcase.common.model.AcceptedEvent;
import com.metalheart.showcase.common.model.DeniedEvent;
import com.metalheart.showcase.common.model.ErrorEvent;
import com.metalheart.showcase.common.model.ResultEvent;
import com.metalheart.showcase.common.model.SuccessEvent;
import com.metalheart.showcase.common.transformer.AcceptedEventToResultEventTransformer;
import com.metalheart.showcase.common.transformer.MessageToEventTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.selector.PayloadTypeSelector;

@Slf4j
@Configuration
public class SenderIntegrationConfiguration {

    @Bean
    public IntegrationFlow senderOutboundMQ(@Qualifier(ReceiverMQConfiguration.TEMPLATE_NAME) AmqpTemplate amqpTemplate,
                                            ApplicationContext ctx) {

        return IntegrationFlows.from(ShowcaseConstants.RECEIVER_CHANNEL)
            .wireTap(f -> f.handle(msg -> log.info("Send event to receiver : " + msg.getPayload())))
            .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(ReceiverMQConfiguration.EXCHANGE_NAME).headersMappedLast(true))
            .get();
    }


    @Bean
    public IntegrationFlow senderInboundMQ(ConnectionFactory connectionFactory,
                                           Receiver2Gateway gateway,
                                           MessageToEventTransformer messageToEvent,
                                           AcceptedEventToResultEventTransformer acceptedEventToResultEvent) {


        return IntegrationFlows
            .from(Amqp.inboundAdapter(connectionFactory, SenderMQConfiguration.QUEUE_NAME))
            .transform(messageToEvent)
            .wireTap(f -> f.handle(msg -> log.info("sender get: " + msg.getPayload())))
            .routeToRecipients(r -> r
                .recipientFlow(new PayloadTypeSelector(AcceptedEvent.class), f -> f
                    .transform(acceptedEventToResultEvent)
                    .handle(m -> gateway.send((ResultEvent)m.getPayload())))
                .recipientFlow(new PayloadTypeSelector(DeniedEvent.class), f -> f.handle(m -> log.info("DeniedEvent")))
                .recipientFlow(new PayloadTypeSelector(SuccessEvent.class), f -> f.handle(m -> log.info("SuccessEvent")))
                .recipientFlow(new PayloadTypeSelector(ErrorEvent.class), f -> f.handle(m -> log.info("ErrorEvent")))
            )
            .get();
    }

    @Bean
    public IntegrationFlow sender2OutboundMQ(@Qualifier(Receiver2MQConfiguration.TEMPLATE_NAME) AmqpTemplate amqpTemplate) {

        return IntegrationFlows.from(ShowcaseConstants.RECEIVER2_CHANNEL)
            .wireTap(f -> f.handle(msg -> log.info("Send event to receiver2 : " + msg.getPayload())))
            .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(Receiver2MQConfiguration.EXCHANGE_NAME).headersMappedLast(true))
            .get();
    }

    @Bean
    public IntegrationFlow senderErrorChannel() {

        return IntegrationFlows.from(ShowcaseConstants.SENDER_ERROR_CHANNEL)
            .wireTap(f -> f.handle(msg -> log.info("Error: " + msg.getPayload())))
            .get();
    }
}
