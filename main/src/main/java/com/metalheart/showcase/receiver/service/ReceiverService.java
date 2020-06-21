package com.metalheart.showcase.receiver.service;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.RequestEvent;

public interface ReceiverService {
    SampleEvent calculate(RequestEvent event);
}
