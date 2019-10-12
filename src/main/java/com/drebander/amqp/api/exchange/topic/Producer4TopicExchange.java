package com.drebander.amqp.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4TopicExchange {
    public static void main(String[] args) throws Exception {
//        创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
//        获取连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
//        发布消息
        String msg1 = "Topic Exchange sends message save ～!";
        String msg2 = "Topic Exchange sends message update ～!";
        String msg3 = "Topic Exchange sends message delete ～!";
//        参数：交换机、routingKey、强制性、立即、属性、内容的字节数组
        channel.basicPublish(exchangeName, routingKey1, false, null, msg1.getBytes());
        channel.basicPublish(exchangeName, routingKey2, false, null, msg2.getBytes());
        channel.basicPublish(exchangeName, routingKey3, false, null, msg3.getBytes());
        channel.close();
        connection.close();
    }
}
