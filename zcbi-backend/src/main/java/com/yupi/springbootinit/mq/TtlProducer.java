package com.yupi.springbootinit.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * SinglePProducer的公开类
 * 用于实现消息发送功能
 * @author zcWing
 */
public class TtlProducer {
    // 定义队列名称为"ttl_queue"
    private final static String QUEUE_NAME = "ttl_queue";

    // 定义程序的入口点：一个公开的静态main方法，它抛出Exception异常
    public static void main(String[] argv) throws Exception {
        // 创建一个ConnectionFactory对象，这个对象可以用于创建到RabbitMQ服务器的连接
        ConnectionFactory factory = new ConnectionFactory();
        // 创建连接工厂
        factory.setHost("192.168.174.152");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // factory.setPort();
        // 使用ConnectionFactory创建一个新的连接,这个连接用于和RabbitMQ服务器进行交互
        try (Connection connection = factory.newConnection();
             // 通过已建立的连接创建一个新的频道
             Channel channel = connection.createChannel()) {
            // 在通道上声明一个队列，我们在此指定的队列名为"hello"
            // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 创建要发送的消息，这里我们将要发送的消息内容设置为"Hello World!"


            // 发送消息
            String message = "Hello World!";
            // 给消息指定过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    // 设置消息的过期时间为1000毫秒
                    .expiration("1000")
                    .build();
            // 发布消息到指定的交换机（"my-exchange"）和路由键（"routing-key"）
            // 使用指定的属性（过期时间）和消息内容（UTF-8编码的字节数组）
            channel.basicPublish("my-exchange", "routing-key", properties, message.getBytes(StandardCharsets.UTF_8));
            // 使用channel.basicPublish方法将消息发布到指定的队列中。这里我们指定的队列名为"hello"
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
