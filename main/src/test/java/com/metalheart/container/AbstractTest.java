package com.metalheart.container;

import com.metalheart.config.RabbitMQTestConfiguration;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    RabbitMQTestConfiguration.class
})
@ActiveProfiles("test")
public class AbstractTest {
    @ClassRule
    public static SampleRabbitContainer rabbitContainer = SampleRabbitContainer.getInstance();
}
