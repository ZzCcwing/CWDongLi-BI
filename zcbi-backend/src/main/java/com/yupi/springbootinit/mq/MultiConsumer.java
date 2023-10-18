package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author zcWing
 */
public class MultiConsumer {
    // 声明队列名称为"multi_queue"
    private static final String TASK_QUEUE_NAME = "multi_queue";

    public static void main(String[] argv) throws Exception {
        // 创建一个新的连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接工厂的主机地址
        factory.setHost("192.168.174.152");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 从工厂获取一个新的连接
        final Connection connection = factory.newConnection();

        for (int i = 0; i < 2; i++) {
            // 从连接获取一个新的通道
            final Channel channel = connection.createChannel();

            // 声明一个队列,并设置属性:队列名称,持久化,非排他,非自动删除,其他参数;如果队列不存在,则创建它
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            // 在控制台打印等待消息的提示信息
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            // (这个先注释)设置预取计数为1，这样RabbitMQ就会在给消费者新消息之前等待先前的消息被确认
            channel.basicQos(1);

            int finalI = i;
            DeliverCallback deliverCallback = (consumerTag, deliver) -> {
                String message = new String(deliver.getBody(), "UTF-8");

                try {
                    System.out.println("[x] Received '" + "编号:" + finalI + ":" + message + "'");
                    // 发送确认消息，确认消息已经被处理
                    channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 发生异常后，拒绝确认消息，发送拒绝消息，并不重新投递消息
                    channel.basicNack(deliver.getEnvelope().getDeliveryTag(), false, false);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
                }
            };
            // 开始消费消息,传入队列名称,是否自动确认,投递回调和消费者取消回调
            channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
        }
    }
}