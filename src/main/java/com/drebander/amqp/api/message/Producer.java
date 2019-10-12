package com.drebander.amqp.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

import static com.sun.deploy.perf.DeployPerfUtil.put;

public class Producer {
    public static void main(String[] args) throws Exception {
//        1、创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Map<String, Object> header = new HashMap<String, Object>() {
            {
                put("my1", "111");
                put("my2", "222");
            }
        };
//        2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
//        3、通过连接获取频道
        Channel channel = connection.createChannel();
//        4、发送消息
        String msg = "Test Message ~!";
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)//2：持久化模式，重启服务，消息依然存在；1：非持久化
                .contentEncoding("utf-8")
                .expiration("10000")//设置过期时间10s
                .headers(header)
                .build();
        for (int i = 0; i < 5; i++) {
            channel.basicPublish("", "test001", true, properties, msg.getBytes());
        }
//        5、关闭连接
        channel.close();
        connection.close();
    }
}
