package com.cd.ums.modules.email.model;

import java.util.Map;

/**
 * Created by hqj on 2018-10-18.
 * 邮件的消息封装类
 */
public class MailMsg {
    private String subject;     // 邮件主题
    private String content;     // 邮件内容
    private String receiverIds; // 邮件接收人id,可以多人,","分隔
    private Boolean audit;      // 是否审核，f不审核，t审核

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

    public String getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(String receiverIds) {
        this.receiverIds = receiverIds;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }
}
