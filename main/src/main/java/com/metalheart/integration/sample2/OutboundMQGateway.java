package com.metalheart.integration.sample2;

import com.metalheart.Constant;
import com.metalheart.integration.sample2.model.SampleEvent;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway(
    defaultRequestChannel = Constant.OUTBOUND_MQ_CHANNEL,
    defaultPayloadExpression = "@objectMapper.writeValueAsString(#args[0])",
    defaultHeaders = {
        @GatewayHeader(name = "contentType", value = "application/json"),
        @GatewayHeader(name = "amqp_type", expression = "#args[0].getType()")
    })
public interface OutboundMQGateway {

    void send(SampleEvent payload);
}
