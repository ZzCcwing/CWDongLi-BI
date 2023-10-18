package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author zcWing
 */
public class DirectConsumer {
    // 定义我们正在监听的交换机名称
    private static final String EXCHANGE_NAME = "direct-exchange";


    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接工厂的主机地址为本地主机
        factory.setHost("192.168.174.152");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 建立与 RabbitMQ 服务器的连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个 direct 类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 创建队列，随机分配一个队列名称，并绑定到 "xiaoyu" 路由键
        String queueName1 = "xiaoyu_queue";
        // 声明队列，设置队列为持久化的，非独占的，非自动删除的
        channel.queueDeclare(queueName1, true, false, false, null);
        // 将队列绑定到指定的交换机上，并指定绑定的路由键为 "xiaoyu"
        channel.queueBind(queueName1, EXCHANGE_NAME, "xiaoyu");

        // 创建队列，随机分配一个队列名称，并绑定到 "xiaopi" 路由键
        String queueName2 = "xiaopi_queue";
        channel.queueDeclare(queueName2, true, false, false, null);
        // 将队列绑定到指定的交换机上，并指定绑定的路由键为 "xiaopi"
        channel.queueBind(queueName2, EXCHANGE_NAME, "xiaopi");
        // 打印等待消息的提示信息
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 创建一个 DeliverCallback 实例来处理接收到的消息（xiaoyu）
        DeliverCallback xiaoyuDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoyu] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 创建一个 DeliverCallback 实例来处理接收到的消息（xiaopi）
        DeliverCallback xiaopiDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaopi] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 开始消费队列中的消息（xiaoyu），设置自动确认消息已被消费
        channel.basicConsume(queueName1, true, xiaoyuDeliverCallback, consumerTag -> {
        });
        // 开始消费队列中的消息（xiaopi），设置自动确认消息已被消费
        channel.basicConsume(queueName2, true, xiaopiDeliverCallback, consumerTag -> {
        });
    }
}
