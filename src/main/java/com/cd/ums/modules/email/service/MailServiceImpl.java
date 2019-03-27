package com.cd.ums.modules.email.service;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.email.api.IMailService;
import com.cd.ums.modules.email.model.MailConfig;
import com.cd.ums.modules.email.model.MailContent;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hqj
 */
public class MailServiceImpl implements IMailService {

    @Override
    public Properties mailInit(MailConfig config) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", config.getSmtpHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        return props;
    }

    /**
     * 读取配置文件内容
     *
     * @return
     */
    private MailConfig getMailConfig() {
        MailConfig config = new MailConfig();
        String smtpHost = Global.getConfig("smtpHost");
        config.setSmtpHost(smtpHost);

        String account = Global.getConfig("account");
        config.setAccount(account);

        String password = Global.getConfig("password");
        config.setPassword(password);

        String sendMailName = Global.getConfig("sendMailName");
        config.setSendMailName(sendMailName);

        String debug = Global.getConfig("debug");
        config.setDebug(Boolean.valueOf(debug));

        return config;
    }

    @Override
    public JsonData sendMail(MailContent content) {
        JsonData jsonData = new JsonData();

        MailConfig config = getMailConfig();
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(mailInit(config));
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(config.isDebug());

        try {
            // 3. 创建一封邮件
            MimeMessage message = null;
            if (content.isAttach()) {
                message = createAttachMail(session, config, content);
            } else {
                message = createSimpleMail(session, config, content);
            }

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
            transport.connect(config.getAccount(), config.getPassword());

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
            jsonData.setSuccess(true).setMessage("发送成功！").setData(message);
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("发送失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param config  发件人配置
     * @param content 收件信息
     * @return
     * @throws Exception
     */
    public static MimeMessage createSimpleMail(Session session, MailConfig config, MailContent content) throws Exception {

        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(config.getAccount(), config.getSendMailName(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        //value 为邮件地址，key为昵称
        for (Map.Entry<String, String> entry : content.getReceivemail().entrySet()) {
            if (message.getRecipients(MimeMessage.RecipientType.TO) == null) {
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(entry.getValue(), entry.getKey(), "UTF-8"));
            } else {
                message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(entry.getValue(), entry.getKey(), "UTF-8"));
            }
        }
        // 抄送给发件人
        message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(config.getAccount(), config.getSendMailName(), "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(content.getSubject(), "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content.getContent(), "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    public static MimeMessage createAttachMail(Session session, MailConfig config, MailContent content) throws Exception {

        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress(config.getAccount(), config.getSendMailName(), "UTF-8"));
        //收件人
        for (Map.Entry<String, String> entry : content.getReceivemail().entrySet()) {
            if (message.getRecipients(MimeMessage.RecipientType.TO) == null) {
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(entry.getValue(), entry.getKey(), "UTF-8"));
            } else {
                message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(entry.getValue(), entry.getKey(), "UTF-8"));
            }
        }
        // 抄送给发件人
        message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(config.getAccount(), config.getSendMailName(), "UTF-8"));
        
        //邮件标题
        message.setSubject(content.getSubject(), "UTF-8");

        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content.getContent(), "text/html;charset=UTF-8");

        //创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(content.getAttachName()));
        attach.setDataHandler(dh);
        attach.setFileName(MimeUtility.encodeText(dh.getName()));

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");
        message.setContent(mp);

        message.setSentDate(new Date());

        message.saveChanges();

        /*//将创建的Email写入到E盘存储
        message.writeTo(new FileOutputStream("E:\\attachMail.eml"));*/

        //返回生成的邮件
        return message;
    }

    public static void main(String[] args) throws Exception {
        MailServiceImpl mail = new MailServiceImpl();
        MailContent content = new MailContent();
        content.setContent("你好, 要求明早8点前到学校");
        content.setSubject("开会通知");
        Map<String, String> map = new HashMap<>();
        map.put("hqj", "150091225@qq.com");
        map.put("zym", "307952767@qq.com");
        //map.put("cyx", "giscyx@163.com");
        content.setReceivemail(map);
        content.setAttach(false);
        content.setAttachName("src\\test_1.jpg");
        content.setAttachName("D:\\java-develop\\project\\ums\\src\\main\\webapp\\userfiles\\20181022221113_btn_add.png");
        mail.sendMail(content);
    }
}
