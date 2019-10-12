package com.drebander.amqp.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Producer4DirectorExchange {
    public static void main(String[] args) throws Exception {
        //        创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
//        创建连接
        Connection connection = connectionFactory.newConnection();
//        获取频道
        Channel channel = connection.createChannel();
        String exchangeName = "test_director_exchange";
        String routingKey = "test.director";
        String msg = "Director message test content ~!";
        channel.basicPublish(exchangeName, routingKey, false, null, msg.getBytes());

        channel.close();
        connection.close();

    }
}
