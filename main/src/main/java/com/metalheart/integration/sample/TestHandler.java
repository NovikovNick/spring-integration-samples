package com.metalheart.integration.sample;

import com.metalheart.model.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestHandler implements GenericHandler<Msg> {

    @Override
    public Object handle(Msg payload, MessageHeaders headers) {

        log.info("SUCCESS!" + payload);

        return true;
    }
}