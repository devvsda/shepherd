package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class NodeExecutorConsumer {

    @Named(ShephardConstants.RabbitMQ.CONSUMER)
    @Inject
    private Connection consumerConnection;

    @Inject
    private RabbitMQOperation rabbitMQOperation;

    public void consume() throws IOException {

        Channel channel = rabbitMQOperation.createChannel(consumerConnection);
        channel.basicQos(3);
        channel.queueDeclare("first_queue", true, false, false, null);
        rabbitMQOperation.consumeMessage(channel, "first_queue", "myConsumerTag",false);

    }
}
