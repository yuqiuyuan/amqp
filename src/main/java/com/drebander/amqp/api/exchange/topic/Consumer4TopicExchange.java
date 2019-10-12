package com.drebander.amqp.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer4TopicExchange {
    public static void main(String[] args) throws Exception {
//        声明一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
//        创建一个连接
        Connection connection = connectionFactory.newConnection();
//        创建一个频道
        Channel channel = connection.createChannel();
//        交换机名称
        String exchangeName = "test_topic_exchange";
//        交换机类型
        String exchangeType = "topic";
//        队列名称
        String queueName = "test_topic_queue";
        String routingKey = "user.*";
        channel.exchangeDeclare(exchangeName, exchangeType, true);
//        参数：队列名称，持久化、专用、自动删除、参数
        channel.queueDeclare(queueName, false, false, false, null);
//        参数：queue、exchange、routingKey
        channel.queueBind(queueName, exchangeName, routingKey);
        QueueingConsumer consumer = new QueueingConsumer(channel);
//        参数：队列名称、是否自动ACK，Consumer
        channel.basicConsume(queueName, true, consumer);
//        循环获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("Test topic exchange consumer:" + msg);
        }

    }
}
