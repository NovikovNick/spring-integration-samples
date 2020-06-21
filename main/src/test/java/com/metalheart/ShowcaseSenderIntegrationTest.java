package com.metalheart;

import com.metalheart.container.AbstractTest;
import com.metalheart.showcase.common.config.ShowcaseConfiguration;
import com.metalheart.showcase.common.model.RequestEvent;
import com.metalheart.showcase.sender.ReceiverGateway;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(classes = ShowcaseConfiguration.class)
public class ShowcaseSenderIntegrationTest extends AbstractTest {

    @Autowired
    private ReceiverGateway gateway;

    @Test
    public void testHappyFlow() throws Exception {

        // act
        log.info("start...");
        gateway.send(new RequestEvent(1));

        TimeUnit.SECONDS.sleep(3);
        log.info("done...");
    }
}
