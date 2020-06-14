package com.metalheart.sample;

import com.metalheart.Constant;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(errorChannel = Constant.SAMPLE_ERROR_CHANNEL)
public interface SampleGateway {

    @Gateway(requestChannel = Constant.SAMPLE_CHANNEL)
    boolean send(Msg msg);
}
