package com.metalheart.showcase.common.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalheart.model.SampleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventToJsonTransformer implements GenericTransformer<SampleEvent, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String transform(SampleEvent source) {

        try {

           return mapper.writeValueAsString(source);

        } catch (Exception e) {

            throw new RuntimeException("Unable to parse event " + source);
        }
    }
}
