package com.metalheart.container;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class SampleRabbitContainer extends GenericContainer<SampleRabbitContainer> {

    private static final String RABBIT_IMAGE = "rabbitmq:3";

    private static SampleRabbitContainer container;
    public static final int RABBIT_MQ_PORT = 5672;

    public SampleRabbitContainer() {
        super(RABBIT_IMAGE);
        waitingFor(Wait.forLogMessage(".*Server startup complete.*", 1));
    }

    public static SampleRabbitContainer getInstance() {
        if (container == null) {
            container = new SampleRabbitContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("RABBIT_HOST_NAME", container.getContainerIpAddress());
        System.setProperty("RABBIT_PORT", String.valueOf(container.getMappedPort(RABBIT_MQ_PORT)));
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
