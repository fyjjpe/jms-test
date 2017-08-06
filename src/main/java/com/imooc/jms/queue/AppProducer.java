package com.imooc.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/8/1.
 * 向ActiveMq提供消息的生产者
 */
public class AppProducer {
    //单机测试
//    private static final String url = "tcp://127.0.0.1:61616";
//    private static final String queueName = "queue-test";

    //集群服务测试
    private static final String url = "failover:(tcp://192.168.63.128:61617,tcp://192.168.63.128:61618)?randomize=true";
    private static final String queueName = "mqs-test";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建连接
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//fasle表示不使用事务控制

        //5.创建一个队列
        Destination destination = session.createQueue(queueName);

        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);

            //8.发布消息
            producer.send(textMessage);

            System.out.println("发送消息" + textMessage.getText());
        }

        //9.关闭连接
        connection.close();
    }
}
