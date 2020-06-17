package com.metalheart.integration.sample;

import com.metalheart.Constant;
import com.metalheart.model.Msg;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(
    errorChannel = Constant.SAMPLE_ERROR_CHANNEL,
    defaultPayloadExpression = "new com.metalheart.model.Msg(#args[0], #args[1])"
)
public interface SampleGateway {

    @Gateway(requestChannel = Constant.SAMPLE_CHANNEL)
    boolean send(Integer id, String payload);
}
