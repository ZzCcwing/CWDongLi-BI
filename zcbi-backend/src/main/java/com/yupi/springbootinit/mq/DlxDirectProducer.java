package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;

/**
 * @author zcWing
 */
public class DlxDirectProducer {
    // 定义死信交换机名称为"dlx-direct-exchange"
    private static final String DEAD_EXCHANGE_NAME = "dlx-direct-exchange";
    // 定义本来的业务交换机名称为"direct2-exchange"
    private static final String WORK_EXCHANGE_NAME = "direct2-exchange";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接工厂的主机地址为本地主机
        factory.setHost("192.168.174.152");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 建立连接并创建通道
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明死信交换机
            channel.exchangeDeclare(DEAD_EXCHANGE_NAME, "direct");

            // 创建老板的死信队列，随机分配一个队列名称
            String queueName1 = "laoban_dlx_queue";
            channel.queueDeclare(queueName1, true, false, false, null);
            channel.queueBind(queueName1, DEAD_EXCHANGE_NAME, "laoban");
            // 创建外包的死信队列，随机分配一个队列名称
            String queueName2 = "waibao_dlx_queue";
            channel.queueDeclare(queueName2, true, false, false, null);
            channel.queueBind(queueName2, DEAD_EXCHANGE_NAME, "waibao");

            // 创建用于处理老板死信队列消息的回调函数，当接收到消息时，拒绝消息并打印消息内容
            DeliverCallback laobanDeliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                // 拒绝消息，并且不要重新将消息放回队列，只拒绝当前消息
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                System.out.println(" [laoban] Received '" +
                        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            };
            // 创建用于处理外包死信队列消息的回调函数，当接收到消息时，拒绝消息并打印消息内容
            DeliverCallback waibaoDeliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                // 拒绝消息，并且不要重新将消息放回队列，只拒绝当前消息
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                System.out.println(" [waibao] Received '" +
                        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            };
            // 注册消费者，用于消费老板的死信队列，绑定回调函数
            channel.basicConsume(queueName1, false, laobanDeliverCallback, consumerTag -> {
            });
            // 注册消费者，用于消费外包的死信队列，绑定回调函数
            channel.basicConsume(queueName2, false, waibaoDeliverCallback, consumerTag -> {
            });
            // 创建一个Scanner对象用于从控制台读取用户输入
            Scanner scanner = new Scanner(System.in);
            // 进入循环，等待用户输入消息和路由键
            while (scanner.hasNext()) {
                // 读取用户输入的一行内容，并以空格分割
                String userInput = scanner.nextLine();
                String[] strings = userInput.split(" ");

                // 如果输入内容不符合要求，继续读取下一行
                if (strings.length < 1) {
                    continue;
                }

                // 获取消息内容和路由键
                String message = strings[0];
                String routingKey = strings[1];
                // 发布消息到业务交换机，带上指定的路由键
                channel.basicPublish(WORK_EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                // 打印发送的消息和路由键
                System.out.println(" [x] Sent '" + message + " with routing:" + routingKey + "'");
            }
        }
    }
}
