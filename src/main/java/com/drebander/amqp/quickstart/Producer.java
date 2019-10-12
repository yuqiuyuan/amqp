package com.drebander.amqp.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception{
//        1、创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
//        2、通过连接工厂创建连接
        Connection connection =connectionFactory.newConnection();
//        3、通过连接获取频道
        Channel channel = connection.createChannel();
//        4、发送消息
        String msg = "Test Message ~!";
        for (int i=0;i<5;i++){
            channel.basicPublish("","test001",true,null,msg.getBytes());
        }
//        5、关闭连接
        channel.close();
        connection.close();
    }
}
