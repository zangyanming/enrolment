package com.cd.ums.modules.email.model;

import java.util.Map;

/**
 * Created by sunlf on 2017-04-05.
 * 邮件的内容封装类
 */
public class MailContent {
    private String subject;//邮件主题
    private String content;//邮件内容
    //邮件接收人,可以多人
    //第一个String是昵称，第二个是邮箱地址
    private Map<String, String> receivemail;
    private boolean isAttach = false;
    private String attachName;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getReceivemail() {
        return receivemail;
    }

    public void setReceivemail(Map<String, String> receivemail) {
        this.receivemail = receivemail;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }
}
