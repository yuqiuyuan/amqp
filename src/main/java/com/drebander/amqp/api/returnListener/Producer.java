package com.drebander.amqp.api.returnListener;

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

        String exchange = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Message";

        channel.addReturnListener(new ReturnListener() {
            /**
             *
             * @param replyCode 响应码
             * @param replyText 响应文本
             * @param exchange 交换机
             * @param routingKey 路由关键字
             * @param basicProperties 基本属性
             * @param body 消息体内容
             * @throws IOException
             */
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                System.out.println("--------------------------handle return-------------------------");
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("basicProperties:"+basicProperties);
                System.out.println("body:"+new String(body));
            }
        });

        /**
         * 第三个参数的含义：是否开启return监，
         * true是开启，把路由不可达的消息返回回来了
         * false是关闭
         */
        channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());

        channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
    }
}
