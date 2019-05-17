package com.devsda.platform.shepherdcore.service.queueservice;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
public class RabbitMqOperations {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqOperations.class);
    private static Connection connection;
    private static Channel channel;

    public static void createQueueConnection(String userName, String password,
                                            String virtualHost, String hostName, Integer portNumber)
            throws IOException, TimeoutException {
        if(connection==null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(userName);
            factory.setPassword(password);
            factory.setVirtualHost(virtualHost);
            factory.setHost(hostName);
            factory.setPort(portNumber);

            connection = factory.newConnection();
        }
        if(channel==null) {
            channel = connection.createChannel();
        }
    }

    public Connection createQueueConnection(String connectionString) throws
            URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {

        if(connection==null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);
            connection = factory.newConnection();
        }
        if(channel==null){
            channel = connection.createChannel();
        }

        return connection;
    }

    public void gracefulShutdown()
            throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public void bindQueue(String exchangeName, String queueName, String routingKey, BuiltinExchangeType exchangeType,Boolean  isDurable)
            throws IOException {
        AMQP.Exchange.DeclareOk exchangeDeclareResponse =channel.exchangeDeclare(exchangeName, exchangeType, isDurable);
        channel.queueDeclare(queueName, isDurable, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        log.info(String.format("exchange %s is declared response %s with queue %s with RoutingKey %s", exchangeName, exchangeDeclareResponse.toString(),queueName, routingKey));
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

    public GetResponse pullMessage(String queueName, Boolean autoAck) throws IOException {
        GetResponse response = channel.basicGet(queueName, autoAck);
        if (response == null) {
            log.info(String.format("No message received from queue %s",queueName));
            return null;
        } else {
            return response;
        }
    }


    public void ackReceivedMessage(long deliveryTag, Boolean multipleAckEnabled) throws IOException {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicAck(deliveryTag, multipleAckEnabled);
    }

    public static String fetchMessageFromGetResponse(GetResponse response){
        byte[] body = response.getBody();
        String responseMessage = new String(body);
        log.info(String.format("following message is received %s",responseMessage));
        return responseMessage;
    }
}

