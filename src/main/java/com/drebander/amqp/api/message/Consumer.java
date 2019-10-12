package com.drebander.amqp.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        1、创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
//        2、创建连接
        Connection connection = connectionFactory.newConnection();
//        3、获取频道
        Channel channel = connection.createChannel();
//        4、获取队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);
//        5、声明消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);
        while (true) {
//          6、获取消息,如果没有消息，这一步将一直会阻塞
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String body = new String(delivery.getBody());
            Map<String, Object> header = delivery.getProperties().getHeaders();
            System.out.println("my1:" + header.get("my1"));
            System.out.println("my2:" + header.get("my2"));
            System.out.println("消费端：" + body);

        }
    }
}
