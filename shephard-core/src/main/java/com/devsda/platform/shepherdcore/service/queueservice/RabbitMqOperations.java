package com.devsda.platform.shepherdcore.service.queueservice;

import com.devsda.platform.shepherdcore.loader.JSONLoader;
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
            log.info(String.format("created a new Connection Id %s on %s:%d/%s",connection.getId(),hostName,portNumber,virtualHost));
        }else{
            log.warn("Connection is already established, No new connection created");
            log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",connection.getId(),connection.getAddress(),connection.getPort(),connection.getServerProperties().toString()));
        }

        // creating channel if it is not already created.
        if(channel==null) {
            channel = connection.createChannel();
            log.info(String.format("created a new channel with channelNumber %d on connectionId %s", channel.getChannelNumber(), connection.getId()));
        }else{
            log.warn("Channel is already present, No new channel created");
            log.info(String.format("channel already present with channelNumer %d",channel.getChannelNumber()));
        }
    }

    public void createQueueConnection(String connectionString) throws
            URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {

        if(connection==null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);
            connection = factory.newConnection();
            log.info(String.format("created a new Connection Id %s on %s:%d",connection.getId(),connection.getAddress(),connection.getPort()));
        }else{
            log.warn("Connection is already established, No new connection created");
            log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",connection.getId(),connection.getAddress(),connection.getPort(),connection.getServerProperties().toString()));
        }

        if(channel==null){
            channel = connection.createChannel();
            log.info(String.format("created a new channel with channelNumber %d on connectionId %s", channel.getChannelNumber(), connection.getId()));
        }else{
            log.warn("Channel is already present, No new channel created");
            log.info(String.format("channel already present with channelNumer %d",channel.getChannelNumber()));
        }
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

    public void purgeAllMessages(String queueName) throws IOException {
        channel.queuePurge(queueName);
    }

    public void publishMessage(String exchangeName, String routingKey, String message) throws IOException {

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
            log.info(String.format("No message received from queue %s", queueName));
            return null;
        }
        return response;
    }


    public void ackReceivedMessage(long deliveryTag, Boolean multipleAckEnabled) throws IOException {
        channel.basicAck(deliveryTag, multipleAckEnabled);
    }

    public static <T> T  fetchMessageFromGetResponse(GetResponse response, Class<T> clazz) throws IOException{
        byte[] body = response.getBody();
        String responseMessage = new String(body);
        log.info(String.format("following message is received %s",responseMessage));
        return JSONLoader.loadFromStringifiedObject(responseMessage,clazz);
    }
}

