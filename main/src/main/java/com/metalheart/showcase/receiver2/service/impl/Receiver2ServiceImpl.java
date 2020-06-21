package com.metalheart.showcase.receiver2.service.impl;

import com.metalheart.model.SampleEvent;
import com.metalheart.showcase.common.model.ErrorEvent;
import com.metalheart.showcase.common.model.ResultEvent;
import com.metalheart.showcase.common.model.SuccessEvent;
import com.metalheart.showcase.receiver2.service.Receiver2Service;
import org.springframework.stereotype.Component;

@Component
public class Receiver2ServiceImpl implements Receiver2Service {

    @Override
    public SampleEvent calculate(ResultEvent event) {
        try {
            switch (event.getData()) {

                case 1:
                    return new SuccessEvent("1 is good");
                default:
                    return new ErrorEvent("error");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
