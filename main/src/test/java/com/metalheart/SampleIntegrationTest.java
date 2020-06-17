package com.metalheart;

import com.metalheart.integration.sample.SampleIntegrationConfiguration;
import com.metalheart.model.Msg;
import com.metalheart.integration.sample.SampleGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SampleIntegrationConfiguration.class)
public class SampleIntegrationTest {


    @Autowired
    private SampleGateway gateway;

    @Test
    public void testHappyFlow() throws Exception {

        // arrange
        int id = 0;
        String payload = "it works...";

        // act
        boolean res = gateway.send(id, payload);

        // assert
        Assert.assertTrue(res);
    }
}
