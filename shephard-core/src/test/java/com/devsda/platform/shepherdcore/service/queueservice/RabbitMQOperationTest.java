package com.devsda.platform.shepherdcore.service.queueservice;

import com.rabbitmq.client.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQOperationTest {

    private static RabbitMQOperation rabbitMQOperation;
    private static Channel channel;

    @BeforeClass
    public static void setup() throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        rabbitMQOperation = new RabbitMQOperation();
    }

    @AfterClass
    public static void tearDown() {

    }
    @Test
    public void connectionTest1() throws Exception {
        Thread object = new Thread(new RabbitMQPublisher());
        object.start();

        Thread object2 = new Thread(new RabbitMQConsumer());
        object2.start();

        object.join();
        object2.join();

    }

    @Test
    public void publishMessageTest() {


    }

    @Test
    public void receiveMessageTest() {

    }

    class RabbitMQPublisher implements Runnable
    {
        public void run()
        {
            try
            {
                // Displaying the thread that is running
                System.out.println ("Thread " +
                        Thread.currentThread().getId() +
                        " is running");


                String connectionString = "amqp://admin:superpassword@3.87.203.156:5672/vhost";
                Connection publisherConnection = rabbitMQOperation.createQueueConnection(connectionString,ConnectionType.publisher);
                Channel channel = rabbitMQOperation.createChannel(publisherConnection);

                rabbitMQOperation.decalareExchangeAndBindQueue(channel,"shepherd_exchange","first-queue","routingKey",BuiltinExchangeType.DIRECT,true,6000);

                // this we need to add outside of the rabbitmq file scope
                boolean autoAck = false;
                channel.addReturnListener(new ReturnListener() {
                    public void handleReturn(int replyCode,
                                             String replyText,
                                             String exchange,
                                             String routingKey,
                                             AMQP.BasicProperties properties,
                                             byte[] body)
                            throws IOException {
                        System.out.println("What the has ");
                    }
                });

                while(true) {
                    String message = "this is message1";
                    String message2 = "this is message 2";

                    channel.basicPublish("shepherd_exchange", "routingKey", true,
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            message.getBytes("UTF-8"));
                    channel.basicPublish("shepherd_exchange", "routingKey", true,
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            message.getBytes("UTF-8"));
                    Thread.sleep(100);
                }
            }
            catch (Exception e)
            {
                // Throwing an exception
                System.out.println ("Exception is caught");
            }
        }
    }

    class RabbitMQConsumer implements Runnable
    {
        public void run()
        {
            try
            {
                // Displaying the thread that is running
                System.out.println ("Thread " +
                        Thread.currentThread().getId() +
                        " is running");

                String connectionString = "amqp://admin:superpassword@3.87.203.156:5672/vhost";
                Connection publisherConnection = rabbitMQOperation.createQueueConnection(connectionString,ConnectionType.consumer);

                Channel channel = rabbitMQOperation.createChannel(publisherConnection);
                channel.basicQos(3);
                channel.queueDeclare("first_queue", true, false, false, null);

                rabbitMQOperation.consumeMessage(channel, "first_queue", "myConsumerTag",false);
            }
            catch (Exception e)
            {
                // Throwing an exception
                e.printStackTrace();
                System.out.println ("Exception is caught");
            }
        }
    }
}


