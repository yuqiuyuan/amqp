package com.drebander.amqp.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
//        1、
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

//        2、获取Connection
        Connection connection = connectionFactory.newConnection();
//        3、通过Connection创建一个Channel
        Channel channel = connection.createChannel();
//        4、指定我们的消息投递模式:消息的确认模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";
//        5、发送一条消息
        String msg = "Hello RabbitMQ Send confirm message!";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

//        6、加上一个确认监听
        channel.addConfirmListener(new ConfirmListener() {

            /**
             * 当消息成功的时候，会进入到这个方法里面
             * @param l 消息的唯一标签
             * @param b
             * @throws IOException
             */
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("-------------------ack!--------------------");
            }

            /**
             * 当消息失败的时候，会进入到这个方法里面
             * @param l
             * @param b
             * @throws IOException
             */
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("-------------------no ack!------------------");
            }

            /**
             * 如果消息发送的过程中出现网络中断的现象，那么最好结合消息百分之百的模式，进行模式设计
             */
        });


    }
}
