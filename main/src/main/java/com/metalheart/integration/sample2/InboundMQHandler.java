package com.metalheart.integration.sample2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InboundMQHandler implements GenericHandler<Object> {

    @Override
    public Object handle(Object payload, MessageHeaders headers) {

        log.info("InboundMQHandler: " + payload.toString());

        return true;
    }
}