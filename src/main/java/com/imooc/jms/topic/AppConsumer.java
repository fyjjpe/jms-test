package com.imooc.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/8/1.
 * 向ActiveMq提供消息的生产者
 */
public class AppConsumer {

    private static final String url = "tcp://127.0.0.1:61616";
    private static final String topicName = "topic-test";

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
        Destination destination = session.createTopic(topicName);

        //6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建一个监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });


        //8.关闭连接(千万不能关闭)
        //connection.close();
    }
}
