package com.devsda.platform.shepherd.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {

    private static RabbitMQOperations rabbitMQOperations;
    private static Channel channel;

    @BeforeClass
    public static void setup() throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        rabbitMQOperations = new RabbitMQOperations();
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void connectionTest() throws Exception {

        String connectionString = "amqp://admin:superpassword@3.87.203.156:5672/vhost";
        Connection connection = rabbitMQOperations.createQueueConnection(connectionString);
        channel = connection.createChannel();
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body)
                    throws IOException {
                System.out.println("What the Fuck");
            }
        });

        GetResponse response = channel.basicGet("first_queue", false);
        if (response == null) {
            // No message retrieved.
        } else {
            AMQP.BasicProperties props = response.getProps();
            byte[] body = response.getBody();
            String responseMessage = new String(body);
            System.out.println(responseMessage);
            long deliveryTag = response.getEnvelope().getDeliveryTag();
            System.out.println("delivery Tag : "+ deliveryTag);
        }

        rabbitMQOperations.purgeAllMessages(channel,"first_queue");
        boolean autoAck = false;
        response = channel.basicGet("first_queue", autoAck);
        if (response == null) {
            // No message retrieved.
        } else {
            AMQP.BasicProperties props = response.getProps();
            byte[] body = response.getBody();
            String responseMessage = new String(body);
            System.out.println(responseMessage);
            long deliveryTag = response.getEnvelope().getDeliveryTag();
            System.out.println("delivery Tag : "+ deliveryTag);
        }

        channel.queueDeclare("first_queue", true, false, false, null);

        String message = "this is message";

        channel.basicPublish("shepherd_exchange", "routingKey",true,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("UTF-8"));

        response = channel.basicGet("first_queue", true);
        if (response == null) {
            // No message retrieved.
        } else {
            AMQP.BasicProperties props = response.getProps();
            byte[] body = response.getBody();
            String responseMessage = new String(body);
            System.out.println(responseMessage);
            long deliveryTag = response.getEnvelope().getDeliveryTag();
            System.out.println("delivery Tag : "+ deliveryTag);
        }

        System.out.println(" [x] Sent '" + message + "'");
    }

    @Test
    public void publishMessageTest() {


    }

    @Test
    public void receiveMessageTest() {

    }
}
