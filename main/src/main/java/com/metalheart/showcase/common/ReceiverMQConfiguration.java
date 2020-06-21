package com.metalheart.showcase.common;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverMQConfiguration {

    public static final String PREFIX = "receiver-";

    public static final String QUEUE_NAME = PREFIX + "queue";
    public static final String EXCHANGE_NAME = PREFIX + "directExchange";
    public static final String BINDING_NAME = PREFIX + "queue-to-exchange-binding";
    public static final String TEMPLATE_NAME = PREFIX + "template";

    @Bean
    @Qualifier(QUEUE_NAME)
    public Queue receiverQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    @Qualifier(EXCHANGE_NAME)
    public Exchange receiverExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    @Qualifier(BINDING_NAME)
    public Binding receiverBinding(@Qualifier(QUEUE_NAME) Queue queue,
                                   @Qualifier(EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME).noargs();
    }

    @Bean
    @Qualifier(TEMPLATE_NAME)
    public RabbitTemplate receiverTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setDefaultReceiveQueue(QUEUE_NAME);
        rabbitTemplate.setRoutingKey(QUEUE_NAME);

        return rabbitTemplate;
    }
}
