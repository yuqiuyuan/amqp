package com.drebander.amqp.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4FanoutExchange {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_fanout_exchange";
        String routingKey = "";
        String msg = "Test fanout msg ~!";
        //  参数：exchange，routingKey，mandatory，immediate，props，msg
        channel.basicPublish(exchangeName, routingKey, false, false, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
