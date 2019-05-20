package com.devsda.platform.shepherdcore.service.queueservice;

import com.devsda.platform.shepherd.model.Node;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.service.NodeExecutor;
import com.devsda.utils.httputils.loader.JsonLoader;
import com.google.inject.Inject;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMqOperation {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqOperation.class);
    private static Connection consumerConnection;
    private static Connection publisherConnection;

    @Inject
    private NodeExecutor nodeExecutor;

    public static Connection createQueueConnection(String userName, String password,
                                            String virtualHost, String hostName, Integer portNumber, ConnectionType type)
            throws IOException, TimeoutException {
        Connection connection = null;
        if(type == ConnectionType.consumer){
            if(consumerConnection==null) {
                consumerConnection = createConnection( userName,  password,
                         virtualHost,  hostName,  portNumber);
                log.info(String.format("created a new Connection Id %s on %s:%d/%s",consumerConnection.getId(),hostName,portNumber,virtualHost));
            }else{
                log.warn("Connection is already established, No new connection created");
                log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",consumerConnection.getId(),consumerConnection.getAddress(),consumerConnection.getPort(),consumerConnection.getServerProperties().toString()));
            }
            connection = consumerConnection;

        }else{
            if(publisherConnection==null){
                publisherConnection = createConnection( userName,  password,
                        virtualHost,  hostName,  portNumber);
                log.info(String.format("created a new Connection Id %s on %s:%d/%s",publisherConnection.getId(),hostName,portNumber,virtualHost));
            }else{
                log.warn("Connection is already established, No new connection created");
                log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",publisherConnection.getId(),publisherConnection.getAddress(),publisherConnection.getPort(),publisherConnection.getServerProperties().toString()));
            }
            connection = publisherConnection;
        }
        return connection;
    }

    public Channel createChannel(Connection connection)throws IOException{
            Channel channel = connection.createChannel();
            log.info(String.format("created a new channel with channelNumber %d on connectionId %s", channel.getChannelNumber(), connection.getId()));
            return channel;
    }

    private static Connection createConnection(String userName, String password,
                             String virtualHost, String hostName, Integer portNumber)throws IOException,TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setHost(hostName);
        factory.setPort(portNumber);

        return factory.newConnection();
    }

    public Connection createQueueConnection(String connectionString, ConnectionType connectionType) throws
            URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        Connection connection = null;

        if(connectionType== ConnectionType.consumer){
            if(consumerConnection==null) {
                consumerConnection = createConnection(connectionString);
                log.info(String.format("created a new Connection Id %s on %s:%d", consumerConnection.getId(), consumerConnection.getAddress(), consumerConnection.getPort()));
            }else{
                log.warn("Connection is already established, No new connection created");
                log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",consumerConnection.getId(),consumerConnection.getAddress(),consumerConnection.getPort(),consumerConnection.getServerProperties().toString()));
            }
            connection = consumerConnection;
        }else{
            if(publisherConnection==null){
                publisherConnection = createConnection(connectionString);
                log.info(String.format("created a new Connection Id %s on %s:%d", publisherConnection.getId(), publisherConnection.getAddress(), publisherConnection.getPort()));
            }else{
                log.warn("Connection is already established, No new connection created");
                log.info(String.format("one connection is already present with Id %s on %s:%d with properties %s",publisherConnection.getId(),publisherConnection.getAddress(),publisherConnection.getPort(),publisherConnection.getServerProperties().toString()));
            }
            connection = publisherConnection;
        }

        return connection;
    }

    private Connection createConnection(String connectionString)throws KeyManagementException,NoSuchAlgorithmException,URISyntaxException,TimeoutException,IOException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(connectionString);
        return factory.newConnection();
    }

    public void gracefulShutdown()
            throws IOException, TimeoutException {
        consumerConnection.close();
        publisherConnection.close();
    }

    public void decalareExchangeAndBindQueue(Channel channel ,String exchangeName, String queueName, String routingKey, BuiltinExchangeType exchangeType,Boolean  isDurable, long messageExpirationTime)
            throws IOException {
        AMQP.Exchange.DeclareOk exchangeDeclareResponse =channel.exchangeDeclare(exchangeName, exchangeType, isDurable);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", messageExpirationTime);
        channel.queueDeclare(queueName, isDurable, false, false, args);
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

    public GetResponse pullMessage(Channel channel, String queueName, Boolean autoAck) throws IOException {
        GetResponse response = channel.basicGet(queueName, autoAck);
        if (response == null) {
            log.info(String.format("No message received from queue %s", queueName));
            return null;
        }
        return response;
    }

    public void consumeMessage(Channel channel, String queueName, String consumerTag, boolean autoAck) throws IOException{
        channel.basicConsume(queueName, autoAck, consumerTag,
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException
                    {
                        long deliveryTag = envelope.getDeliveryTag();

                        try {
                            String message = new String(body);
                            Node nodeToExecute = JSONLoader.loadFromStringifiedObject(message, Node.class);

                            nodeExecutor.call(nodeToExecute);

                            System.out.println("Message received : "+ message + "delivery Tag "+ deliveryTag);
                        } catch (Exception ex) {
                            System.out.println("Unable to convert byte[] message in string");
                            ex.printStackTrace();
                            // TODO : NACK
                        }

                        if(!autoAck) {
                            channel.basicAck(deliveryTag, true);
                        }
                    }
                });
    }


    public void ackReceivedMessage(Channel channel,long deliveryTag, Boolean multipleAckEnabled) throws IOException {
        channel.basicAck(deliveryTag, multipleAckEnabled);
    }

    public static <T> T  fetchMessageFromGetResponse(GetResponse response, Class<T> clazz) throws IOException{
        byte[] body = response.getBody();
        String responseMessage = new String(body);
        log.info(String.format("following message is received %s",responseMessage));
        return JSONLoader.loadFromStringifiedObject(responseMessage,clazz);
    }
}

