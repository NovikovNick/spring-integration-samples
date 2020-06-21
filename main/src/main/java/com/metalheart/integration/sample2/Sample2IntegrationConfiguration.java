package com.metalheart.integration.sample2;

import com.metalheart.Constant;
import com.metalheart.integration.sample2.handler.InboundMQEventAHandler;
import com.metalheart.integration.sample2.handler.InboundMQEventBHandler;
import com.metalheart.integration.sample2.model.SampleEventA;
import com.metalheart.integration.sample2.model.SampleEventB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.selector.PayloadTypeSelector;

@Slf4j
@Configuration
@EnableIntegration
@ComponentScan("com.metalheart.integration.sample2")
@IntegrationComponentScan("com.metalheart.integration.sample2")
public class Sample2IntegrationConfiguration {

    public static final String QUEUE_NAME = "sample2-queue";
    public static final String EXCHANGE_NAME = "sample2-directExchange";
    public static final String BINDING_NAME = "sample2-queue-to-exchange-binding";
    public static final String TEMPLATE_NAME = "sample2-template";

    @Bean
    @Qualifier(QUEUE_NAME)
    public Queue coldStatsTasksQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    @Qualifier(EXCHANGE_NAME)
    public Exchange internalEventsExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    @Qualifier(BINDING_NAME)
    public Binding internalEventsBinding(
        @Qualifier(QUEUE_NAME) Queue queue,
        @Qualifier(EXCHANGE_NAME) Exchange exchange
    ) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME).noargs();
    }

    @Bean
    @Qualifier(TEMPLATE_NAME)
    public RabbitTemplate delayedRabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setDefaultReceiveQueue(QUEUE_NAME);
        rabbitTemplate.setRoutingKey(QUEUE_NAME);

        return rabbitTemplate;
    }

    // INTEGRATION

    @Bean
    public IntegrationFlow inboundMQ(ConnectionFactory connectionFactory,
                                     MessageToEventTransformer messageToEvent,
                                     InboundMQEventAHandler aHandler,
                                     InboundMQEventBHandler bHandler) {


        return IntegrationFlows
            .from(Amqp.inboundAdapter(connectionFactory, QUEUE_NAME)
                .configureContainer(config -> config.concurrentConsumers(1)))
            .transform(messageToEvent)
            .routeToRecipients(r -> r

                .recipient("A_CHANNEL", new PayloadTypeSelector(SampleEventA.class))

                .recipientFlow(new PayloadTypeSelector(SampleEventB.class), f -> f
                    .handle(bHandler)
                    .handle(m -> log.info("handled as B")))

            )
            .get();
    }

    @Bean
    public IntegrationFlow outboundMQ(@Qualifier(TEMPLATE_NAME) AmqpTemplate amqpTemplate) {

        return IntegrationFlows.from(Constant.OUTBOUND_MQ_CHANNEL)
            .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(EXCHANGE_NAME).headersMappedLast(true))
            .get();
    }


    @Bean
    public IntegrationFlow aChannel(InboundMQEventAHandler aHandler) {

        return IntegrationFlows
            .from("A_CHANNEL")
            .handle(aHandler)
            .handle((m) -> log.info("handled as A"))
            .get();
    }
}
