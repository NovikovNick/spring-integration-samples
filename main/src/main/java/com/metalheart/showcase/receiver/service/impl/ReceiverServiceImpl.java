package com.metalheart.showcase.receiver.service.impl;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.AcceptedEvent;
import com.metalheart.showcase.common.model.DeniedEvent;
import com.metalheart.showcase.common.model.RequestEvent;
import com.metalheart.showcase.receiver.service.ReceiverService;
import org.springframework.stereotype.Component;

@Component
public class ReceiverServiceImpl implements ReceiverService {

    @Override
    public SampleEvent calculate(RequestEvent event) {
        try {
            switch (event.getData()) {

                case 1:
                    return new AcceptedEvent(event.getData());
                default:
                    return new DeniedEvent("error");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
