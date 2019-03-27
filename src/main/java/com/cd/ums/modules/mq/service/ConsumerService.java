package com.cd.ums.modules.mq.service;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.mq.api.IConsumerService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqj on 2018/9/29.
 */
@Service
public class ConsumerService implements IConsumerService {

    @Override
    public JsonData getAllMessage(String queueName) {
        JsonData jsonData = new JsonData();
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            //创建一连接工厂
            String brokerURL = Global.getConfig("brokerURL");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
            //创建一个连接
            connection = connectionFactory.createConnection();
            //打开连接
            connection.start();
            //创建一个回话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个目的地Destination
            Queue queue = session.createQueue(queueName);
            //创建一个消费者
            consumer = session.createConsumer(queue);
            List<String> list = new ArrayList<String>();
            while (true) {
                //设置接收者接收消息的时间，这里定为1s
                Message message = consumer.receive(1000);
                if (message != null) {
                    //message.
                    list.add(message.toString());
                } else {
                    //超时结束
                    break;
                }
            }

            jsonData.setSuccess(true);
            jsonData.setMessage("消息接收成功！");
            jsonData.setData(list);
            jsonData.setTotalCount(list.size());
        } catch (JMSException e) {
            jsonData.setSuccess(false).setMessage("消息接收失败！原因：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("消息接收失败！原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null)
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            if (consumer != null)
                try {
                    consumer.close();
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
