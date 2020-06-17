package com.metalheart.integration.sample2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalheart.integration.sample2.model.SampleEvent;
import com.metalheart.integration.sample2.model.SampleEventA;
import com.metalheart.integration.sample2.model.SampleEventB;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;
import static org.springframework.amqp.support.AmqpHeaders.CONTENT_TYPE;

@Slf4j
@Component
public class MessageToEventTransformer implements GenericTransformer<Message, SampleEvent> {

    @Autowired
    private ObjectMapper mapper;

    private Map<String, Class<?>> eventTypeBindings = Map.of(
        SampleEventA.EVENT_TYPE, SampleEventA.class,
        SampleEventB.EVENT_TYPE, SampleEventB.class
    );

    @Override
    public SampleEvent transform(Message source) {

        if (CONTENT_TYPE_JSON.equals(source.getHeaders().get(CONTENT_TYPE))) {
            try {

                String type = (String) source.getHeaders().get("amqp_type");

                if (eventTypeBindings.containsKey(type)) {

                    byte[] payload = (byte[]) source.getPayload();

                    SampleEvent event = (SampleEvent) mapper.readValue(payload, eventTypeBindings.get(type));
                    return event;
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return null;
    }
}
