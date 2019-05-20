package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.service.queueservice.RabbitMqOperation;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class NodeExecutorConsumer {

    @Named("consumer")
    @Inject
    private Connection consumerConnection;

    @Inject
    private RabbitMqOperation rabbitMqOperation;

    public void consume() throws IOException {

        Channel channel = rabbitMqOperation.createChannel(consumerConnection);
        channel.basicQos(3);
        channel.queueDeclare("first_queue", true, false, false, null);
        rabbitMqOperation.consumeMessage(channel, "first_queue", "myConsumerTag",false);

    }
}
