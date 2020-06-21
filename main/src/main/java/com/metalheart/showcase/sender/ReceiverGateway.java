package com.metalheart.showcase.sender;

import com.metalheart.showcase.ShowcaseConstants;
import com.metalheart.showcase.common.model.RequestEvent;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway(
    defaultRequestChannel = ShowcaseConstants.RECEIVER_CHANNEL,
    errorChannel = ShowcaseConstants.SENDER_ERROR_CHANNEL,
    defaultPayloadExpression = "@objectMapper.writeValueAsString(#args[0])",
    defaultHeaders = {
        @GatewayHeader(name = "contentType", value = "application/json"),
        @GatewayHeader(name = "amqp_type", expression = "#args[0].getType()")
    })
public interface ReceiverGateway {

    void send(RequestEvent request);
}
