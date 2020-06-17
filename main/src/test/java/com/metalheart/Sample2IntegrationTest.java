package com.metalheart;

import com.metalheart.container.AbstractTest;
import com.metalheart.integration.sample2.OutboundMQGateway;
import com.metalheart.integration.sample2.Sample2IntegrationConfiguration;
import com.metalheart.integration.sample2.model.SampleEvent;
import com.metalheart.integration.sample2.model.SampleEventA;
import com.metalheart.integration.sample2.model.SampleEventB;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(classes = Sample2IntegrationConfiguration.class)
public class Sample2IntegrationTest extends AbstractTest {

    @Autowired
    private OutboundMQGateway gateway;

    @Test
    public void testHappyFlow() throws Exception {

        // act
        gateway.send(SampleEventA.builder().payload("payload_A").type(SampleEventA.EVENT_TYPE).build());
        gateway.send(SampleEventB.builder().payload(4).type(SampleEventB.EVENT_TYPE).build());

        /*CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        Executors.newSingleThreadExecutor().execute(() -> completableFuture.complete(
            agentInteractionGateway.reply(ReplyOutboundMail.builder().build())));
        await().atMost(1, TimeUnit.SECONDS).until(completableFuture::isDone);*/
        // assert

        TimeUnit.SECONDS.sleep(1);
        log.info("done...");
    }
}
