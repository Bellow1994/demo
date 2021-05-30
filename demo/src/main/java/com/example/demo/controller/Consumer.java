package com.example.demo.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer {

    public static ConnectionFactory getConnectionFactory() {
        // 创建连接工程，下面给出的是默认的case
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        return factory;
    }

    public static void main(String[] args) throws IOException, TimeoutException  {
        ConnectionFactory connectionFactory = getConnectionFactory();
        Connection newConnection = null;
        Channel createChannel = null;
        try {
            newConnection = connectionFactory.newConnection();
            createChannel = newConnection.createChannel();
            /**
             * 声明一个队列。
             * 参数一：队列名称
             * 参数二：是否持久化
             * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
             * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
             * 参数五：队列的附加属性
             * 注意：
             * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
             * 2.队列名可以任意取值，但需要与消息接收者一致。
             * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
             */
            createChannel.queueDeclare("queue1", true, false, false,null);
            /**
             * 接收消息。会持续坚挺，不能关闭channel和Connection
             * 参数一：队列名称
             * 参数二：消息是否自动确认，true表示自动确认接收完消息以后会自动将消息从队列移除。否则需要手动ack消息
             * 参数三：消息接收者的标签，用于多个消费者同时监听一个队列时用于确认不同消费者。
             * 参数四：消息接收者
             */
            createChannel.basicConsume("queue1", true, "", new DefaultConsumer(createChannel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                        byte[] body) throws IOException {
                    String string = new String(body, "UTF-8");
                    System.out.println("接收到d消息： -》 " + string);
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        
    }
}