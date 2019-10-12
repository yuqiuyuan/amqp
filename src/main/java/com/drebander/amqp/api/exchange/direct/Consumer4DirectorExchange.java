package com.drebander.amqp.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer4DirectorExchange {

    public static void main(String[] args) throws Exception {
//        创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
//         自动重连
        connectionFactory.setAutomaticRecoveryEnabled(true);
//        网络重连的设置，超时时间3秒的时候进行重连
        connectionFactory.setNetworkRecoveryInterval(3000);
//        创建连接
        Connection connection = connectionFactory.newConnection();
//        获取频道
        Channel channel = connection.createChannel();
        String exchangeName = "test_director_exchange";
        String exchangeType = "direct";
        String queueName = "test_director_queue";
        String routingKey = "test.director";
//        声明一个交换机
//        参数：交换机名称、交换类型、是否持久化、自动删除、是否属于内部服务费、参数
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
//        声明一个队列
//        参数：队列名称、是否持久化、是否专用队列、是否自动删除、参数
        channel.queueDeclare(queueName, false, false, false, null);
//        创建一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);
//        声明消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
//        参数：队列名称，是否自动接收，消费者
        channel.basicConsume(queueName, true, queueingConsumer);
//        循环获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("Direct-消费端：" + msg);
        }
    }
}
