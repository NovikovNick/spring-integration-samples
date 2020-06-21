package com.metalheart.showcase.receiver2.service;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.RequestEvent;
import com.metalheart.showcase.common.model.ResultEvent;

public interface Receiver2Service {
    SampleEvent calculate(ResultEvent event);
}
