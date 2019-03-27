package com.cd.ums.modules.mq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import javax.jms.Queue;

public class Test {

    public void testQueueProducer() throws Exception {
        // 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
        // brokerURL服务器的ip及端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://140.143.55.78:61616");
        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://HQJ:61616");
        // 第二步：使用ConnectionFactory对象创建一个Connection对象。
        Connection connection = connectionFactory.createConnection();
        // 第三步：开启连接，调用Connection对象的start方法。
        connection.start();
        // 第四步：使用Connection对象创建一个Session对象。
        // 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        // 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        // 参数：队列的名称。
        //Queue queue = session.createQueue("test-queue");
        Queue queue = session.createQueue("aaa");
        // 第六步：使用Session对象创建一个Producer对象。
        MessageProducer producer = session.createProducer(queue);
        // 第七步：创建一个Message对象，创建一个TextMessage对象。
        /*TextMessage message = new ActiveMQTextMessage();
        message.setText("hello activeMq,this is my first test.");*/
        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my first test.");
        // 第八步：使用Producer对象发送消息。
        producer.send(textMessage);
        // 第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
    }

    public void testSpringQueueProducer() throws Exception {
        String msg = "spring activemq test";
        String msg1 = "spring activemq test123";
        // 第一步：初始化一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context-activemq.xml");
        // 第二步：从容器中获得JMSTemplate对象。
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 第三步：从容器中获得一个Destination对象
        Queue queue = (Queue) applicationContext.getBean("queueDestination");
        Queue queue1 = new ActiveMQQueue("aaa");
        // 第四步：使用JMSTemplate对象发送消息，需要知道Destination
        jmsTemplate.send(queue, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg);
                return textMessage;
            }
        });
        jmsTemplate.send(queue1, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg1);
                return textMessage;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        try {
            test.testQueueProducer();
            //test.testSpringQueueProducer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }
}
