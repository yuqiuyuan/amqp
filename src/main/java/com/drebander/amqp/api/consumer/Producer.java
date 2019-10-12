package com.drebander.amqp.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * return机制生产者
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_consumer_exchange";
        String routingKey = "consumer.save";

        String msg = "Hello RabbitMQ Return Message";

        /**
         * 第三个参数的含义：是否开启return监，
         * true是开启，把路由不可达的消息返回回来了
         * false是关闭
         */
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
        }

    }
}
