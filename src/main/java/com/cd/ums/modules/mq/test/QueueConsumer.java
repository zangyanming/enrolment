package com.cd.ums.modules.mq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by hqj on 2018/9/28.
 * 同步消费
 */
public class QueueConsumer {
    public static void main(String[] args) {
        //创建一连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://140.143.55.78:61616");
        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://HQJ:61616");
        try {
            //创建一个连接
            Connection connection = connectionFactory.createConnection();
            //打开连接
            connection.start();
            //创建一个回话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个目的地Destination
            Queue queue = session.createQueue("mytestqueue");
            Queue queue1 = session.createQueue("spring-queue");
            Queue queue2 = session.createQueue("2-8b4290830ef94a7594542aa8fbf403a5");
            //创建一个消费者
            MessageConsumer consumer = session.createConsumer(queue2);
            while (true) {
                //设置接收者接收消息的时间，为了便于测试，这里定为100s
                Message message = consumer.receive(100000);
                if (message !=null) {
                    System.out.println("aaa=" + message);
                    System.out.println(message.toString());
                } else {
                    //超时结束
                    break;
                }
            }
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
