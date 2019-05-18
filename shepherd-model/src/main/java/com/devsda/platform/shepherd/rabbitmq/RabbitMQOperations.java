package com.devsda.platform.shepherd.rabbitmq;

import com.rabbitmq.client.*;

import javax.print.URIException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQOperations {

    public Connection createQueueConnection(String userName, String password,
                                            String virtualHost, String hostName, Integer portNumber)
            throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setHost(hostName);
        factory.setPort(portNumber);

        Connection connection = factory.newConnection();
        return connection;
    }

    public Connection createQueueConnection(String connectionString) throws
            URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(connectionString);
        Connection connection = factory.newConnection();
        return connection;
    }

    public void gracefulShutdown(Connection connection, Channel channel)
            throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public void bindQueue(Channel channel, String exchangeName, String queueName, String routingKey, boolean isDurable)
            throws IOException {
       AMQP.Exchange.DeclareOk exchangeDeclareResponse =channel.exchangeDeclare(exchangeName, "direct", true);

        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void bindQueueWithMultipleComnsumers(Channel channel, String exchangeName,
                                                String queueName, String routingKey) throws IOException {
        channel.exchangeDeclare(exchangeName, "direct", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void deleteQueue(Channel channel, String queueName, Boolean ifUnused, Boolean ifEmpty) throws IOException {
        channel.queueDelete(queueName, ifUnused, ifEmpty);
    }

    public void purgeAllMessages(Channel channel, String queueName) throws IOException {
        channel.queuePurge(queueName);
    }

    public void publishMessage(Channel channel, String exchangeName, String routingKey, String message) throws IOException {

        if (message == null || message.equals("")) {
            return;
        }

        byte[] messageBodyBytes = message.getBytes();
        channel.basicPublish(exchangeName, routingKey,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                messageBodyBytes);
    }

    public void receiveMessage(Channel channel, String queueName) throws IOException {

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
    }
}
