package com.metalheart.showcase.common.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.AcceptedEvent;
import com.metalheart.showcase.common.model.ResultEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AcceptedEventToResultEventTransformer implements GenericTransformer<AcceptedEvent, ResultEvent> {

    @Override
    public ResultEvent transform(AcceptedEvent source) {

        return new ResultEvent(source.getData());
    }
}
