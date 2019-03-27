package com.cd.ums.modules.wx.model;

/**
 * Created by hqj on 2018-10-31.
 * 微信的消息封装类
 */
public class WxMsg {
    private String content;     // 微信文本内容
    private String receiverIds; // 微信接收人id,可以多人,","分隔
    private Boolean audit;      // 是否审核，f不审核，t审核

    private String receiverOpenIds;

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

    public String getReceiverOpenIds() {
        return receiverOpenIds;
    }

    public void setReceiverOpenIds(String receiverOpenIds) {
        this.receiverOpenIds = receiverOpenIds;
    }
}
