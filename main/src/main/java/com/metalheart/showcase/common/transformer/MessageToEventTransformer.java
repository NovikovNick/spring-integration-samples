package com.metalheart.showcase.common.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.AcceptedEvent;
import com.metalheart.showcase.common.model.DeniedEvent;
import com.metalheart.showcase.common.model.ErrorEvent;
import com.metalheart.showcase.common.model.RequestEvent;
import com.metalheart.showcase.common.model.ResultEvent;
import com.metalheart.showcase.common.model.SuccessEvent;
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

        RequestEvent.EVENT_TYPE, RequestEvent.class,
        ResultEvent.EVENT_TYPE, ResultEvent.class,


        AcceptedEvent.EVENT_TYPE, AcceptedEvent.class,
        DeniedEvent.EVENT_TYPE, DeniedEvent.class,

        SuccessEvent.EVENT_TYPE, SuccessEvent.class,
        ErrorEvent.EVENT_TYPE, ErrorEvent.class
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
