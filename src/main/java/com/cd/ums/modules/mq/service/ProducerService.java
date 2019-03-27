package com.cd.ums.modules.mq.service;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.mq.api.IProducerService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by hqj on 2018/9/29.
 */
@Service
public class ProducerService implements IProducerService {

    @Override
    public JsonData sendMessage(String queueName, String msg) {
        JsonData jsonData = new JsonData();
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            // 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
            // brokerURL服务器的ip及端口号
            String brokerURL = Global.getConfig("brokerURL");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
            // 第二步：使用ConnectionFactory对象创建一个Connection对象。
            connection = connection = connectionFactory.createConnection();
            // 第三步：开启连接，调用Connection对象的start方法。
            connection.start();
            // 第四步：使用Connection对象创建一个Session对象。
            // 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
            // 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
            // 参数：队列的名称。
            Queue queue = session.createQueue(queueName);
            // 第六步：使用Session对象创建一个Producer对象。
            producer = session.createProducer(queue);
            // 第七步：创建一个Message对象，创建一个TextMessage对象。
            /*TextMessage message = new ActiveMQTextMessage();
            message.setText("hello activeMq,this is my first test.");*/
            TextMessage textMessage = session.createTextMessage(msg);
            // 第八步：使用Producer对象发送消息。
            producer.send(textMessage);

            jsonData.setSuccess(true);
            jsonData.setMessage("消息发送成功！");
            jsonData.setTotalCount(1);
        } catch (JMSException e) {
            jsonData.setSuccess(false);
            jsonData.setMessage("消息发送失败！原因：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            jsonData.setSuccess(false);
            jsonData.setMessage("消息发送失败！原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 第九步：关闭资源。
            if (producer != null)
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            if (session != null)
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            return jsonData;
        }
    }
}
