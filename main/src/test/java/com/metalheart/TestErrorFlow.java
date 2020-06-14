package com.metalheart;

import com.metalheart.config.IntegrationConfig;
import com.metalheart.sample.Msg;
import com.metalheart.sample.SampleGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class TestErrorFlow {


    @Autowired
    private SampleGateway gateway;

    @Test
    public void testHappyFlow() throws Exception {

        // act
        Msg msg = new Msg("it works...");
        boolean res = gateway.send(msg);
        gateway.send(msg);

        // assert
        Assert.assertTrue(res);
    }

    @Test
    public void testHappyFlow2() throws Exception {

        // Arrange
        // Mockito.when(handler.handle(any(), any())).thenReturn(true);

        // act
        Msg msg = new Msg("it works...");
        boolean res = gateway.send(msg);

        // assert
        Assert.assertTrue(res);
    }
}
